package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

import bean.Member;
import jp.pay.model.Charge;

public class ProceedsDAO extends DAO {
	public boolean insert(Charge charge, Member member, String price) throws Exception {
		Connection con = getConnection();
		con.setAutoCommit(false);
		// 購入日時を生成
		Date now = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String s = f.format(now);
		
		PreparedStatement st = con.prepareStatement(
			"insert into proceeds (CHARGE_ID, DATE, MEMBER_ID, TOTALPRICE) values(?, ?, ?, ?)");
		st.setString(1, charge.getId());
		st.setString(2, s);
		st.setInt(3, member.getId());
		st.setString(4, price);
		int line = st.executeUpdate();
		st.close();

		if (line != 1) {
			con.rollback();
			con.setAutoCommit(true);
			con.close();
			return false;
		}
		
		con.commit();
		con.setAutoCommit(true);
		con.close();
		return true;
	}
}
