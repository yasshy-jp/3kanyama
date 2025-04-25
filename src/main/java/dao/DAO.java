package dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
// コネクションオブジェクト（プール）の取得機能
public class DAO {
	
	private static DataSource ds; // 各DAOインスタンスで共有
	private static final String JNDI_NAME = "java:/comp/env/jdbc/mikan"; // JNDI 名を定数化
	/* 
	 * ダブルチェックロッキングやクラスレベルのロックにより、dsの初期化を確実に1回だけにする。
	 * ダブルチェックにより、初期化が済んだ後は、ほとんどの呼び出しで synchronized に入らずに済むため高速。
	*/
	public Connection getConnection() throws SQLException {
		// ①スレッドAとBが同時通過の可能性あり
		if (ds == null) {
			// ②ここで排他制御（クラスレベルでロック。マルチスレッドによる多数のds生成を防止）
			synchronized (DAO.class) {
				// ③スレッドAが先に入るとスレッドBは必ずfalseになるため最初のスレッドしか進めない
				if (ds == null) { // 2回目のチェック。スレッドBが後で入ってもAが初期化済みのため、再初期化が防止できる。
					try {
						InitialContext ic = new InitialContext();
						ds = (DataSource) ic.lookup(JNDI_NAME); // データソース取得：P197参照
					} catch (NamingException e) {
						throw new SQLException("データソースの取得に失敗しました", e);
					}
				}
			}
		}
		return ds.getConnection();
	}
}
