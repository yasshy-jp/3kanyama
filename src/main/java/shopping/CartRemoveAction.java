package shopping;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.Item;
import dao.ProductStockRegisterDAO;
import tool.Action;
//カート内の商品を削除するクラス
public class CartRemoveAction extends Action {
	@SuppressWarnings("unchecked")
	public String execute(HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		int id = Integer.parseInt(request.getParameter("id"));  // 商品ID
		int totalPrice_taxIn = (int)session.getAttribute("TOTALPRICE_TAXIN"); // 合計金額（税込）	
		int totalCount = (int)session.getAttribute("TOTALCOUNT"); // 合計個数
		List<Item> cart = (List<Item>)session.getAttribute("CART"); // 商品カート
		
		for (Item item : cart) {
			if (item.getProduct().getId() == id) {
				// カート内の合計個数と金額を再計算
				totalPrice_taxIn -= (int)(item.getProduct().getPrice() * item.getCount() * 1.1);
				totalCount -= item.getCount();
				// セッションスコープへ格納
				session.setAttribute("TOTALPRICE_TAXIN", totalPrice_taxIn);
				session.setAttribute("TOTALCOUNT", totalCount);
				
				// 商品DBの在庫の更新
				int stock = item.getProduct().getStock() + item.getCount();
				ProductStockRegisterDAO psrdao = new ProductStockRegisterDAO();
				int line = psrdao.r️egister(id, stock);
				if (line != 1) return "stock-register-error.jsp";
				
				// セッションに保存中の商品（"LIST"の要素）の在庫更新
				item.getProduct().setStock(stock);
				// 動作確認用コード
				System.out.println("「" + item.getProduct().getName() + "」を削除。在庫が「"
				+ item.getProduct().getStock() + "個」に復活。");
				
				cart.remove(item);
				break;
			}
		}
		return "cart.jsp";
	}
}
