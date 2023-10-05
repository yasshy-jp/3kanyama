package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Product;

public class ProductDAO extends DAO {
	// 商品検索機能
	public List<Product> search(String keyword, int category) throws Exception {
		List<Product> list=new ArrayList<>();

		Connection con=getConnection();

		PreparedStatement st=con.prepareStatement(
			"SELECT * FROM FARMPRODUCT WHERE NAME LIKE ? OR CATEGORY_ID=?");
		st.setString(1, "%"+keyword+"%");
		st.setInt(2, category);
		ResultSet rs=st.executeQuery(); // DBの検索結果を取得するメソッド。結果はオブジェクトで返す。(p202)

		while (rs.next()) {
			Product p=new Product();
			p.setId(rs.getInt("product_id"));
			p.setName(rs.getString("name"));
			p.setPrice(rs.getInt("price"));
			list.add(p);
		}

		st.close();
		con.close();

		return list;
	}

	// 商品追加機能
	public int insert(Product product) throws Exception {
		Connection con=getConnection();

		PreparedStatement st=con.prepareStatement(
			"INSERT INTO FARMPRODUCT (CATEGORY_ID, NAME, PRICE) VALUES(?, ?, ?)");
		st.setInt(1, product.getId());
		st.setString(2, product.getName());
		st.setInt(3, product.getPrice());
		int line=st.executeUpdate();// DBの内容を変更するメソッド。変更された行数を返す。(p210)

		st.close();
		con.close();
		return line;
	}
}
