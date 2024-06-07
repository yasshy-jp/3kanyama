package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/******* 商品在庫の更新 *******/
public class ProductStockRegisterDAO extends DAO {
	public int r️egister(int id, int recount) throws Exception {
		int line = 0;
		try {
			Connection con = getConnection();
			con.setAutoCommit(false);

			PreparedStatement st = con.prepareStatement("UPDATE FARMPRODUCT SET STOCK=? WHERE PRODUCT_ID=?");
			st.setInt(1, recount);
			st.setInt(2, id);
			st.executeUpdate();

			st = con.prepareStatement("SELECT * FROM FARMPRODUCT WHERE STOCK=? && PRODUCT_ID=?");
			st.setInt(1, recount);
			st.setInt(2, id);
			ResultSet rs = st.executeQuery();
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
