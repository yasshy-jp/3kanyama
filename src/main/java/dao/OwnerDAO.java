package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.Owner;

public class OwnerDAO extends DAO {
	/******* オーナー認証機能 *******/
	public Owner search(String login, String password)
		throws Exception {
		Owner owner = null;

		Connection con=getConnection();

		PreparedStatement st;
		st=con.prepareStatement(
			"select * from owner where login=? AND password=?");
		st.setString(1, login);
		st.setString(2, password);
		ResultSet rs=st.executeQuery();

		while (rs.next()) {
			owner = new Owner();
			owner.setId(rs.getInt("owner_id"));
			owner.setName(rs.getString("name"));
		}

		st.close();
		con.close();
		return owner;
	}
			
}
