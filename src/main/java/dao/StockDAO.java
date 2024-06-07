package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Stock;

public class StockDAO extends DAO {
	/******* 在庫リスト（商品リスト） *******/
	public List<Stock> stockStatus() throws Exception {
		// 在庫リストをコレクション（List）で用意
		List<Stock> stocklist=new ArrayList<>();

		Connection con=getConnection();

		PreparedStatement st;
		st=con.prepareStatement(
			// 人間が理解し易いよう、商品DBと品目DBを結合。品目ID列を品目名とした在庫リストを得る。
			"select P.product_id, H.category_name, P.name, P.price, P.stock from farmproduct as P join category as H on P.category_id = H.category_id"
			);
		ResultSet rs=st.executeQuery();

		while (rs.next()) {
			Stock stock=new Stock();
			stock.setId(rs.getInt("product_id"));
			stock.setKategoryName(rs.getString("category_name"));
			stock.setName(rs.getString("name"));
			stock.setPrice(rs.getInt("price"));
			stock.setStock(rs.getInt("stock"));
			stocklist.add(stock);
		}

		st.close();
		con.close();
		return stocklist;
	}
			
}
