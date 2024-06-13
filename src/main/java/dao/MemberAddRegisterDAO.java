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

			PreparedStatement st=con.prepareStatement("update member set customer_id=?, card_id=? where member_id=?");
			st.setString(1, customer.getId());	
			st.setString(2, card.getId());
			st.setInt(3, member.getId());
			st.executeUpdate();

			st=con.prepareStatement("select * from member where customer_id=?");
			st.setString(1, customer.getId());
			ResultSet rs=st.executeQuery();
			while (rs.next()) {
				member.setCustomer_id(rs.getString("customer_id"));
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
