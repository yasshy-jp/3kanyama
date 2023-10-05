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
			// データソース名を設定(jdbc/LoftyNail)(p198 xml設定ファイルより)
			ds=(DataSource)ic.lookup("java:/comp/env/jdbc/SBCreative");
		}
		return ds.getConnection();
	}
}
