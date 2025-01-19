package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/******* 商品在庫の更新 *******/
public class ProductStockRegisterDAO extends DAO {
	public int r️egister(int id, int stock) throws Exception {
		int line = 0;
		try {
			Connection con = getConnection();
			con.setAutoCommit(false);

			PreparedStatement st = con.prepareStatement("update farmproduct set stock=? where product_id=?");
			st.setInt(1, stock);
			st.setInt(2, id);
			st.executeUpdate();

			st = con.prepareStatement("select * from farmproduct where stock=? && product_id=?");
			st.setInt(1, stock);
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
