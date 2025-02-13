package owner;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.Owner;
import bean.Stock;
import dao.GetStockListDAO;
import tool.Action;

/*** 在庫管理画面の取得 ***/
public class StockStatusAction extends Action {
	public String execute(HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		Owner owner = (Owner) session.getAttribute("OWNER"); // オーナーオブジェクト
		// ログイン確認
		if (owner == null) {
			return "owner-error-login.jsp";
		}
		// 在庫リストの取得
		GetStockListDAO stdao = new GetStockListDAO();
		List<Stock> stockList = stdao.stockStatus();

		session.setAttribute("STOCKLIST", stockList);
		return "stockstatus.jsp";
	}
}
