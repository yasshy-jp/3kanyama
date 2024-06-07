package owner;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Owner;
import bean.Stock;
import dao.StockDAO;
import tool.Action;

public class StockStatusAction extends Action {
	public String execute(
		HttpServletRequest request, HttpServletResponse response
	) throws Exception {
		
		HttpSession session = request.getSession();
		Owner owner = (Owner)session.getAttribute("OWNER");  // オーナーオブジェクト
		// ログイン確認
		if (owner == null) {
			return "owner-error-login.jsp";
		}

		StockDAO stdao = new StockDAO();
		List<Stock> stocklist = stdao.stockStatus();

		session.setAttribute("STOCKLIST", stocklist);
		return "stockstatus.jsp";
	}
	
}
