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
			"SELECT * FROM OWNER WHERE LOGIN=? AND PASSWORD=?");
		st.setString(1, login);
		st.setString(2, password);
		ResultSet rs=st.executeQuery();

		while (rs.next()) {
			owner = new Owner();
			owner.setId(rs.getInt("OWNER_ID"));
			owner.setLogin(rs.getString("LOGIN"));
			owner.setPassword(rs.getString("PASSWORD"));
			owner.setName(rs.getString("NAME"));
		}

		st.close();
		con.close();
		return owner;
	}
			
}
