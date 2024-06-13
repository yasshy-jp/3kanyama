package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Order;

public class OrderDAO extends DAO {
	/******* 受注状況表示 *******/
	public List<Order> OrderStatus() throws Exception {
		List<Order> orderlist=new ArrayList<>();

		Connection con=getConnection();

		PreparedStatement st;
		st=con.prepareStatement(
			"select c.date, m.simei, c.charge_id, c.totalprice, p.name, p.price, o.count "
			+ "from proceeds as c join purchase as o on c.charge_id = o.charge_id"
			+ " join farmproduct as p on o.product_id = p.product_id "
			+ "join member as m on c.member_id = m.member_id;"
			);
		ResultSet rs=st.executeQuery();

		while (rs.next()) {
			Order order=new Order();
			order.setDate(rs.getString("date"));
			order.setSimei(rs.getString("simei"));
			order.setCharge_id(rs.getString("charge_id"));
			order.setTotalprice(rs.getInt("totalprice"));
			order.setName(rs.getString("name"));
			order.setPrice(rs.getInt("price"));
			order.setCount(rs.getInt("count"));
			orderlist.add(order);
		}

		st.close();
		con.close();
		return orderlist;
	}
			
}
