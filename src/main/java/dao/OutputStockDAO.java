package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import exception.StockUpdateException;

/*** 商品DBの在庫の更新（出庫時：カートに追加 or 購入確定） ***/
public class OutputStockDAO extends DAO {
	// Logger を使い、詳細なエラーログを適切に記録
	private static final Logger LOGGER = Logger.getLogger(OutputStockDAO.class.getName());

	public int outputStock(int id, int receivedQuantity) throws StockUpdateException {
		// プレースホルダで商品IDを設定（SQLインジェクションなど安全対策）
		// FOR UPDATE で商品IDの行を排他ロック。複数スレッドが同時に在庫を減らさないよう、後発スレッドは待たされる。
		String selectQuery = "select stock from farmproduct where product_id = ? for update";
        String updateQuery = "update farmproduct set stock = ? where product_id = ?";
        // 出庫数を引いた新たな在庫を格納する変数
        int newStock = 0;
        Connection con = null;
        
        try {
        	con = getConnection(); // コミットモード確認は con.getAutoCommit()
        	con.setAutoCommit(false); // 手動コミット設定（トランザクション管理の開始）

        	// 1. 在庫の確認と減算
        	// try-with-resouces 構文でリソースを自動開放。例外ハンドリングは外側のtry-catch構文が担う。
        	try (PreparedStatement selectSt = con.prepareStatement(selectQuery)) {
        		selectSt.setInt(1, id); // プレースホルダへ値をセット
            	try (ResultSet rs = selectSt.executeQuery()) {
                    if (!rs.next() || rs.getInt("stock") < receivedQuantity) {
                    	LOGGER.warning("在庫不足（商品ID: " + id + "）");
                    	con.rollback();
                    	return -1; // 在庫不足
                    } else {
                    	newStock = rs.getInt("stock") - receivedQuantity;
					}
            	} // ここで rs.close()
            } // ここで selectSt.close()
        	
            // 2. 在庫を更新
        	try (PreparedStatement updateSt = con.prepareStatement(updateQuery)) {
        		updateSt.setInt(1, newStock);
        		updateSt.setInt(2, id);
        		int updatedRows = updateSt.executeUpdate();
            
        		if (updatedRows == 1) {
        			con.commit();
        			return newStock; // 成功
        		} else {
        			LOGGER.warning("在庫更新失敗（商品ID: " + id + "）");
        			con.rollback();
        			return 0; // 失敗
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
        	/* 
        	 * 自作例外を投げ直す理由
        	 * try-catchした例外は、throws宣言があっても投げ直さなければ呼び出し元に伝搬しない。
        	 * 疎結合な強靭な設計とするため、低レベル例外（SQLException）を自作例外に変換（包んで抽象化）
        	 * 例外ラップ（例外連鎖）で意味変換と原因保持の両立を図る。
        	 * メソッド内で例外処理を完結してもよいが、今回は大規模・長期運用PJを意識してこの例外ハンドリングを採用した。
        	 */
        	throw new StockUpdateException("在庫更新中にDBエラーが発生しました。", e);
        } finally {
        	/* リソースの後片付け
        	 * 最初にnullチェックをすることでNullPointer例外による意図しないクラッシュを防止
        	 * setAutoCommit(true)とclose()を別々のtry-catchでラップ（確実に処理を実行するため）
        	 * 同じtry-catchに2つの処理を配置してた場合、最初処理でエラーが出るとcatchに飛び、次の処理がスキップされる。
        	 * */
            if (con != null) {
            	try {
            		// コネクション状態を元の自動コミットモードに戻し、次の利用者に正しく渡す。
            		// Tomcatでコネクションプール利用の場合、con.close()で自動実行されるが環境によるため一応明示
                    con.setAutoCommit(true);
                } catch (SQLException autoCommitEx) {
                    LOGGER.severe("autoCommit のリセットに失敗: " + autoCommitEx.getMessage());
                }
            	try {
            		// コネクションプールに接続を戻す（開放）
            		con.close();
            	} catch (SQLException closeEx) {
            		LOGGER.severe("コネクションプール返却失敗: " + closeEx.getMessage());
            	}
            }
        }
    }
}