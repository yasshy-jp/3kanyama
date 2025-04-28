package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Product;
//商品検索機能
public class ProductSearchDAO extends DAO {
	
	public List<Product> search(int category, String keyword) throws Exception {
		List<Product> list=new ArrayList<>();

		Connection con=getConnection(); // コネクションオブジェクト（プール）の取得

		// SQL文をDBへ送るためのPreparedStatementオブジェクトの生成。
		PreparedStatement st=con.prepareStatement(
			"select * from farmproduct where name like ? or category_id=?");
		st.setString(1, "%"+keyword+"%");
		st.setInt(2, category);
		
		// PreparedStatementオブジェクトのSQL文の実行。結果が格納されたResultSetオブジェクトが返る。
		ResultSet rs=st.executeQuery();

		while (rs.next()) {
			Product p=new Product();
			p.setId(rs.getInt("product_id"));
			p.setName(rs.getString("name"));
			p.setPrice(rs.getInt("price"));
			p.setStock(rs.getInt("stock"));
			list.add(p);
		}

		st.close();
		con.close();

		return list;
	}

}