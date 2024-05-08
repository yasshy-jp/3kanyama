package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.Member;

public class MemberSearchDAO extends DAO {
	/******* 会員認証機能 *******/
	public Member search(String login, String password)
		throws Exception {
		Member member = null;

		Connection con=getConnection();

		PreparedStatement st;
		st=con.prepareStatement(
			"SELECT * FROM MEMBER WHERE LOGIN=? AND PASSWORD=?");
		st.setString(1, login);
		st.setString(2, password);
		ResultSet rs=st.executeQuery();

		while (rs.next()) {
			member = new Member();
			member.setId(rs.getInt("MEMBER_ID"));
			/*
			DBをMySQLへ変更に伴う一時的なコメントアウト
			member.setCustomer_id(rs.getString("CUSTOMER_ID"));
			member.setCard_id(rs.getString("CARD_ID"));
			*/
			member.setSimei(rs.getString("SIMEI"));
		}

		st.close();
		con.close();
		return member;
	}
	
}
