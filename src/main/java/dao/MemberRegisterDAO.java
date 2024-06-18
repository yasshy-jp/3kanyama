package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.Member;

public class MemberRegisterDAO extends DAO {
	/***** 新規会員登録機能 *****/
	public int r️egister(Member member) throws Exception {
		int line = 0;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            con.setAutoCommit(false);

            // インサートの準備と実行
            st = con.prepareStatement("insert into member (login, password, simei, furigana, mail, tel, postcode, prefecture, city, address) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
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
            st.close();

            // インサート結果の確認
            st = con.prepareStatement("select * from member where login = ?");
            st.setString(1, member.getLogin());
            rs = st.executeQuery();
            while (rs.next()) {
                line++;
            }

            if (line == 1) {
                con.commit();
            } else {
                con.rollback();
            }

            con.setAutoCommit(true);
        } catch (Exception e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (Exception rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception rsEx) {
                    rsEx.printStackTrace();
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (Exception stEx) {
                    stEx.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception conEx) {
                    conEx.printStackTrace();
                }
            }
        }
        return line;
	}
		
}
