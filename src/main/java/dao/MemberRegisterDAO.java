package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.Member;

public class MemberRegisterDAO extends DAO {
	/***** 新規会員登録機能 *****/
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
		
}
