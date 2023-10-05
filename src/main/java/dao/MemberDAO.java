package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.Member;
import jp.pay.model.Card;
import jp.pay.model.Customer;

public class MemberDAO extends DAO {
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
			member.setLogin(rs.getString("LOGIN"));
			member.setPassword(rs.getString("PASSWORD"));
			member.setCustomer_id(rs.getString("CUSTOMER_ID"));
			member.setCard_id(rs.getString("CARD_ID"));
			member.setSimei(rs.getString("SIMEI"));
		}

		st.close();
		con.close();
		return member;
	}
	
	/******* 新規会員登録機能 *******/
	public int r️egister(Member member) throws Exception {
		int line=0;
		try {
			Connection con=getConnection();
			con.setAutoCommit(false);

			PreparedStatement st=con.prepareStatement("INSERT INTO MEMBER (LOGIN, PASSWORD, SIMEI, FURIGANA, "
					+ "MAIL, TEL, POSTCODE, PREFECTURE, CITY, ADDRESS) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			st.setString(1, member.getLogin());
			st.setString(2, member.getPassword());
			st.setString(3, member.getSimei());
			st.setString(4, member.getFurigana());
			st.setString(5, member.getMail());
			st.setString(6, member.getTel());
			st.setString(7, member.getPostcode());
			st.setString(8, member.getPrefecture());
			st.setString(9, member.getCity());
			st.setString(10, member.getAddress());
			st.executeUpdate();

			st=con.prepareStatement("SELECT * FROM MEMBER WHERE LOGIN=?");
			st.setString(1, member.getLogin());
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
				member.setCustomer_id(rs.getString("CUSTOMER_ID"));
				member.setCard_id(rs.getString("CARD_ID"));
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
