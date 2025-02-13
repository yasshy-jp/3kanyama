package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import exception.StockUpdateException;

// 商品DBの在庫確認と減算（カートに追加時）
public class UpdateStockDAO extends DAO {
	// Logger を使い、詳細なエラーログを適切に記録
	private static final Logger LOGGER = Logger.getLogger(UpdateStockDAO.class.getName());

	public int updateStock(int id, int addQuantity) throws StockUpdateException {
		String selectQuery = "select stock from farmproduct where product_id = ? for update";
        String updateQuery = "update farmproduct set stock = stock - ? where product_id = ?";
        Connection con = null;
        
        try {
        	con = getConnection();
//        	System.out.println("コミットモード: " + con.getAutoCommit()); // true：自動、false：手動
        	con.setAutoCommit(false); // 手動コミット設定（トランザクション開始）

        	// 1. 在庫を確認
        	// 複数スレッドが同時に在庫を減らす時のデータの整合性を保つため、排他ロックをかける（FOR UPDATE）
        	try (PreparedStatement selectSt = con.prepareStatement(selectQuery)) {
        		selectSt.setInt(1, id);
            	try (ResultSet rs = selectSt.executeQuery()) {
                    if (!rs.next() || rs.getInt("stock") < addQuantity) {
                    	LOGGER.warning("在庫不足（商品ID: " + id + "）");
                    	con.rollback();
                    	return -1; // 在庫不足
                    }
            	} // ここで rs.close()
            } // ここで selectSt.close()
        	
            // 2. 在庫を減算
        	try (PreparedStatement updateSt = con.prepareStatement(updateQuery)) {
        		updateSt.setInt(1, addQuantity);
        		updateSt.setInt(2, id);
        		int updatedRows = updateSt.executeUpdate();
            
        		if (updatedRows == 1) {
        			con.commit();
        			return 1; // 成功
        		} else {
        			LOGGER.warning("在庫更新失敗（商品ID: " + id + "）");
        			con.rollback();
        			return -1; // 在庫不足
        		}
            } // ここで updateSt.close()
        } catch (SQLException e) { // 独自のカスタム例外 StockUpdateException の作成を検討
        	if (con == null) {
                LOGGER.severe("コネクションプール取得失敗: " + e.getMessage());
            } else {
        		try {
        			con.rollback();  // 変更をキャンセル
        			LOGGER.info("トランザクションのロールバック実行");
        		} catch (SQLException rollbackEx) {
        			LOGGER.severe("トランザクションのロールバックに失敗: " + rollbackEx.getMessage());
        			// 未コミット（ロールバック失敗）のトランザクションはMySQLではcon.close()時に自動でロールバックされる
        		}
        	}
        	LOGGER.severe("在庫更新エラー（SQLState: " + e.getSQLState() + ", 商品ID: " + id + "）: " + e.getMessage());
        	throw new StockUpdateException("在庫更新中にDBエラーが発生しました。", e);  // 呼び出し元に例外を投げる
        } finally {
            if (con != null) {
            	try {
                    con.setAutoCommit(true);
                    // Tomcatでコネクションプール利用の場合、con.close()で自動実行されるが環境によるため一応明示
                } catch (SQLException autoCommitEx) {
                    LOGGER.severe("autoCommit のリセットに失敗: " + autoCommitEx.getMessage());
                }
            	try {
            		con.close();
            	} catch (SQLException closeEx) {
            		LOGGER.severe("コネクションプール返却失敗: " + closeEx.getMessage());
            	}
            }
        }
    }
}