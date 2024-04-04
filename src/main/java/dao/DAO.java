package dao;

import java.sql.Connection;

import javax.naming.InitialContext;
import javax.sql.DataSource;
//コネクションオブジェクト（プール）の取得
public class DAO {
	//各DAOで共有されるクラスフィールド
	static DataSource ds;

	public Connection getConnection() throws Exception {
		if (ds==null) {
			InitialContext ic=new InitialContext();
			// データソース名（jdbc/mikanyama）を設定（p197 xml設定ファイルより）
			ds=(DataSource)ic.lookup("java:/comp/env/jdbc/mikanyama");
		}
		return ds.getConnection();
	}
}
