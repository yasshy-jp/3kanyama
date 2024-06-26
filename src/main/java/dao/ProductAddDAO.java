package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import bean.Product;
//商品追加機能
public class ProductAddDAO extends DAO {
	
	public int insert(Product product) throws Exception {
		Connection con=getConnection();

		PreparedStatement st=con.prepareStatement(
			"insert into farmproduct (category_id, name, price, stock) value(?, ?, ?, ?)");
		st.setInt(1, product.getKategoryId());
		st.setString(2, product.getName());
		st.setInt(3, product.getPrice());
		st.setInt(4, product.getStock());
		int line=st.executeUpdate();// DBの内容を変更するメソッド。変更された行数を返す。(p210)

		st.close();
		con.close();
		return line;
	}
}
