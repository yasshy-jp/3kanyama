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
			"SELECT C.PROCEEDS_ID, C.DATE, M.SIMEI, C.CHARGE_ID, C.TOTALPRICE, P.NAME, P.PRICE, O.COUNT FROM PROCEEDS AS C JOIN PURCHASE AS O ON C.CHARGE_ID = O.CHARGE_ID JOIN FARMPRODUCT AS P ON O.PRODUCT_ID = P.PRODUCT_ID JOIN MEMBER AS M ON C.MEMBER_ID = M.MEMBER_ID;"
			);
		ResultSet rs=st.executeQuery();

		while (rs.next()) {
			Order order=new Order();
			order.setId(rs.getInt("PROCEEDS_ID"));
			order.setDate(rs.getString("DATE"));
			order.setSimei(rs.getString("SIMEI"));
			order.setCharge_id(rs.getString("CHARGE_ID"));
			order.setTotalprice(rs.getInt("TOTALPRICE"));
			order.setName(rs.getString("NAME"));
			order.setPrice(rs.getInt("PRICE"));
			order.setCount(rs.getInt("COUNT"));
			orderlist.add(order);
			System.out.println(order.getId() + " : " + order.getDate());
		}

		st.close();
		con.close();
		return orderlist;
	}
			
}
