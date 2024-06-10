package owner;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Stock;
import dao.ProductStockRegisterDAO;
import tool.Action;

// 商品在庫の追加処理
public class StockRecountAction extends Action {

	@SuppressWarnings("unchecked")
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		// 在庫変更する商品IDの取得
		int id = Integer.parseInt(request.getParameter("id"));
		// 在庫変更する数量の取得
		int stadd = Integer.parseInt(request.getParameter("stadd"));
		// 現在の在庫(Bean)を格納した在庫リストをセッション属性から取得
		List<Stock> stocklist = (List<Stock>) session.getAttribute("STOCKLIST");
		// 更新された在庫数を格納する変数を用意
		int recount = 0;

		for (Stock stock : stocklist) {
			if (stock.getId() == id) {
				// 在庫変動分を加減算
				recount = stock.getStock() + stadd;
				if (recount < 0) {
					recount = 0;
				}
				// 更新された在庫数をセッション属性の在庫インスタンスにセット
				stock.setStock(recount);
				break;	
			}
		}
		// 更新された在庫数を商品DBへ登録
		ProductStockRegisterDAO psrdao = new ProductStockRegisterDAO();
		int line = psrdao.r️egister(id, recount);

		if (line != 1) {
			return "/owner/stock-register-error.jsp";
		}

		return "stockstatus.jsp";
	}
}
