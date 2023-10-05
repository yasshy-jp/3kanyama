package owner;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Order;
import bean.Owner;
import dao.OrderDAO;
import tool.Action;

public class OrderStatusAction extends Action {
	public String execute(
		HttpServletRequest request, HttpServletResponse response
	) throws Exception {
		
		HttpSession session = request.getSession();
		Owner owner = (Owner)session.getAttribute("OWNER");  // オーナーオブジェクト
		// ログイン確認
		if (owner == null) {
			return "owner-error-login.jsp";
		}

		OrderDAO daorder = new OrderDAO();
		List<Order> orderlist = daorder.OrderStatus();

		session.setAttribute("ORDERLIST", orderlist);
		return "orderstatus.jsp";
	}
	
}
