package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import bean.Item;
import bean.Product;
import jp.pay.model.Charge;

public class PurchaseDAO extends DAO {
	public boolean insert(Charge charge, List<Item> cart) throws Exception {
		Connection con = getConnection();
		con.setAutoCommit(false);
		
		for (Item item : cart) {
			PreparedStatement st = con.prepareStatement(
				"insert into purchase (charge_id, product_id, count) values(?, ?, ?)");
			st.setString(1, charge.getId());
			Product p = item.getProduct();
			st.setInt(2, p.getId());
			st.setInt(3, item.getCount());
			int line = st.executeUpdate();
			st.close();

			if (line != 1) {
				con.rollback();
				con.setAutoCommit(true);
				con.close();
				return false;
			}
		}

		con.commit();
		con.setAutoCommit(true);
		con.close();
		return true;
	}
}
