package shopping;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.Item;
import dao.InputStockDAO;
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
				int returnedQuantity = item.getCount();

				// 商品DBの在庫の更新（返却）
				InputStockDAO isdao = new InputStockDAO();
				int newStock = isdao.inputStock(id, returnedQuantity);
				switch (newStock) {
				case -1:
					return "stockShortageError.jsp"; // 商品不明
				case 0:
					return "cartRemoveError.jsp"; // 在庫更新エラー
				default:
					// カート内の合計個数と金額を再計算
					totalPrice_taxIn -= (int)(item.getProduct().getPrice() * item.getCount() * 1.1);
					totalCount -= item.getCount();
					// 商品画面出力用にセッションで管理中の在庫を更新（"LIST"の要素：List<Product>）
					item.getProduct().setStock(newStock);
				}								
				// 動作確認用コード
				System.out.println("「" + item.getProduct().getName() + "」を削除。在庫が「"
				+ item.getProduct().getStock() + "個」に復活。");
				
				cart.remove(item);
				break;
			}
		}
		// セッションスコープへ格納
		session.setAttribute("TOTALPRICE_TAXIN", totalPrice_taxIn);
		session.setAttribute("TOTALCOUNT", totalCount);
		return "cart.jsp";
	}
}
