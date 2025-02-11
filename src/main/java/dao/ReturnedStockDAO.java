package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

import exception.StockUpdateException;

//商品DBに在庫を戻す（カートから削除時）
public class ReturnedStockDAO extends DAO {
	// Logger を使い、詳細なエラーログを適切に記録
	private static final Logger LOGGER = Logger.getLogger(UpdateStockDAO.class.getName());

	public int returnedStk(int id, int returnedQuantity) throws StockUpdateException {
		String selectQuery = "select stock from farmproduct where product_id = ? for update";
        String updateQuery = "update farmproduct set stock = stock + ? where product_id = ?";
        Connection con = null;
        
        try {
        	con = getConnection();
        	con.setAutoCommit(false); // 手動コミット設定（トランザクション開始）
        	
        	// 1. 競合対策
        	// UPDATE 文だけでは「読み取り→更新」間の競合を防げないため、排他ロックをかける（FOR UPDATE）
        	try (PreparedStatement selectSt = con.prepareStatement(selectQuery)) {
        		selectSt.setInt(1, id);
            	selectSt.executeQuery(); // 行ロックを取得
        	}	

            // 2. 在庫を戻す
        	try (PreparedStatement updateSt = con.prepareStatement(updateQuery)) {
        		updateSt.setInt(1, returnedQuantity);
        		updateSt.setInt(2, id);
        		int updatedRows = updateSt.executeUpdate();
            
        		if (updatedRows == 1) {
        			con.commit();
        			return 1; // 成功
        		} else {
        			LOGGER.warning("在庫更新失敗（商品ID: " + id + "）");
        			con.rollback();
        			return -1; // 失敗
        		}
            } // ここで updateSt.close()
        } catch (SQLException e) { // 独自のカスタム例外 StockUpdateException の作成を検討
        	if (con == null) {
                LOGGER.severe("コネクションプール取得失敗: " + e.getMessage());
            } else {
        		try {
        			con.rollback();  // 変更をキャンセル
        			LOGGER.info("トランザクションをロールバック");
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