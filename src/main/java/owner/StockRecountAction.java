package owner;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.Stock;
import dao.InputStockDAO;
import tool.Action;

/*** 商品在庫の補充機能 ***/
public class StockRecountAction extends Action {
	@SuppressWarnings("unchecked")
	public String execute(HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		List<Stock> stocklist = (List<Stock>) session.getAttribute("STOCKLIST"); // 商品兼在庫リスト
		int id = Integer.parseInt(request.getParameter("id")); // 商品ID
		int addStockQuantity = Integer.parseInt(request.getParameter("addStockQuantity")); // 数量
		int newStock = 0; // 更新後の在庫

		// 商品DBの在庫の更新（補充）
		InputStockDAO isdao = new InputStockDAO();
		newStock = isdao.inputStock(id, addStockQuantity);
		switch (newStock) {
		case -1:
			return "stockShortageError.jsp"; // 商品不明
		case 0:
			return "stockUpdateError.jsp"; // 在庫更新エラー
		default:
			// レスポンス用にセッションで管理中の在庫を更新（"LIST"の要素：List<Stock>）
			for (Stock stock : stocklist) {
				if (stock.getId() == id) {
					stock.setStock(newStock);
					break;
				}
			}
		}
		return "stockstatus.jsp";
	}
}
