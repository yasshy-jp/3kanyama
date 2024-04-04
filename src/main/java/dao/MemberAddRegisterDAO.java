package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.Member;
import jp.pay.model.Card;
import jp.pay.model.Customer;

public class MemberAddRegisterDAO extends DAO {
	/******* 会員情報の追加機能（PAY.JPで作成した顧客ID＆カードID）*******/
	public int r️egister(Customer customer, Card card, Member member) throws Exception {
		int line=0;
		try {
			Connection con=getConnection();
			con.setAutoCommit(false);

			PreparedStatement st=con.prepareStatement("UPDATE MEMBER SET CUSTOMER_ID=?, CARD_ID=? WHERE MEMBER_ID=?");
			st.setString(1, customer.getId());	
			st.setString(2, card.getId());
			st.setInt(3, member.getId());
			st.executeUpdate();

			st=con.prepareStatement("SELECT * FROM MEMBER WHERE CUSTOMER_ID=?");
			st.setString(1, customer.getId());
			ResultSet rs=st.executeQuery();
			while (rs.next()) {
				line++;
			}

			if (line == 1) {
				con.commit();
			} else {
				con.rollback();
			}

			con.setAutoCommit(true);

			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return line;
	}
	
}
