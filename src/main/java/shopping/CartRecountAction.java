package shopping;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.Item;
import dao.ProductStockRegisterDAO;
import tool.Action;
// カート内商品の数量変更を行うクラス
public class CartRecountAction extends Action {
	@SuppressWarnings("unchecked")
	public String execute(HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		int id = Integer.parseInt(request.getParameter("id")); // 商品ID
		int recount = Integer.parseInt(request.getParameter("recount")); // 変更数量
		List<Item> cart = (List<Item>)session.getAttribute("CART"); // 商品カート
		int totalPrice_taxIn = (int)session.getAttribute("TOTALPRICE_TAXIN"); // 合計金額（税込）
		int totalCount = (int)session.getAttribute("TOTALCOUNT"); // 合計個数
		
		for (Item item : cart) {
			if (item.getProduct().getId() == id) {
				// 数量増加時
				if (item.getCount() < recount) {
					int incrementCount = recount - item.getCount();  // 増加個数
					totalPrice_taxIn += (int)(item.getProduct().getPrice() * incrementCount * 1.1);
					totalCount += incrementCount;
					// カート内商品の個数の更新（"CART"の要素）					
					item.setCount(recount);
					
					// 商品DBの在庫の更新（在庫減少）
					int stock = item.getProduct().getStock() - incrementCount;
					ProductStockRegisterDAO psrdao = new ProductStockRegisterDAO();
					int line = psrdao.r️egister(id, stock);
					if (line != 1) return "stock-register-error.jsp";
					
					// セッションに保存中の商品（"LIST"の要素）の在庫の更新
					item.getProduct().setStock(stock);
					// 動作確認用コード
					System.out.println("カート内で「" + item.getProduct().getName() + 
							"」の個数追加により、在庫が「" + item.getProduct().getStock() + "個」に減少。");
										
				// 数量減少時
				} else if (item.getCount() > recount) {
					int decrementCount = item.getCount() - recount;  // 減少個数
					totalPrice_taxIn -= (int)(item.getProduct().getPrice() * decrementCount * 1.1);
					totalCount -= decrementCount;
					// カート内商品の個数の更新（"CART"の要素）
					item.setCount(recount);
					
					// 商品DBの在庫の更新（在庫復活）
					int stock = item.getProduct().getStock() + decrementCount;
					ProductStockRegisterDAO psrdao = new ProductStockRegisterDAO();
					int line = psrdao.r️egister(id, stock);
					if (line != 1) return "stock-register-error.jsp";
					
					// セッションに保存中の商品（"LIST"の要素）の在庫の更新
					item.getProduct().setStock(stock);
					// 動作確認用コード
					System.out.println("カート内で「" + item.getProduct().getName() + 
							"」の個数減少により、在庫が「" + item.getProduct().getStock() + "個」に復活。");
					
				} else {
					return "cart.jsp";
				}
				
				// 税込み合計金額を再計算し、各種合計データをスコープへ再格納
				session.setAttribute("TOTALPRICE_TAXIN", totalPrice_taxIn);
				session.setAttribute("TOTALCOUNT", totalCount);
				break;
			}
		}
		return "cart.jsp";
	}
}