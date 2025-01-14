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
			"select * from member where login=? and password=?");
		st.setString(1, login);
		st.setString(2, password);
		ResultSet rs=st.executeQuery();

		if (rs.next()) {
			member = new Member();
			member.setId(rs.getInt("member_id"));
			member.setCustomer_id(rs.getString("customer_id"));
			member.setCard_id(rs.getString("card_id"));
			member.setSimei(rs.getString("simei"));
		}

		st.close();
		con.close();
		return member;
	}
	
}
