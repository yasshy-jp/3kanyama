package shopping;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.Item;
import dao.InputStockDAO;
import dao.OutputStockDAO;
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
					int additionalQuantity = recount - item.getCount();  // 追加個数
					
					// 商品DBの在庫の更新（出庫）
					OutputStockDAO udsdao = new OutputStockDAO();
					int newStock = udsdao.outputStock(id, additionalQuantity);
					switch (newStock) {
					case -1:
						return "stockShortageError.jsp"; // 在庫不足
					case 0:
						return "stockUpdateError.jsp"; // 在庫更新エラー
					default:
						totalPrice_taxIn += (int)(item.getProduct().getPrice() * additionalQuantity * 1.1);
						totalCount += additionalQuantity;
						item.setCount(recount); // カート内商品の個数更新
						// 商品画面出力用にセッションで管理中の在庫を更新（"LIST"の要素：List<Product>）
						item.getProduct().setStock(newStock);
					}
					// 動作確認用コード
					System.out.println("カート内で「" + item.getProduct().getName() + 
							"」の個数追加により、在庫が「" + item.getProduct().getStock() + "個」に減少。");
										
				// 数量減少時
				} else if (item.getCount() > recount) {
					int returnedQuantity = item.getCount() - recount;  // 返却個数
					
					// 商品DBの在庫の更新（返却）
					InputStockDAO isdao = new InputStockDAO();
					int newStock = isdao.inputStock(id, returnedQuantity);
					switch (newStock) {
					case -1:
						return "stockShortageError.jsp"; // 商品不明
					case 0:
						return "stockUpdateError.jsp"; // 在庫更新エラー
					default:
						totalPrice_taxIn -= (int)(item.getProduct().getPrice() * returnedQuantity * 1.1);
						totalCount -= returnedQuantity;
						item.setCount(recount); // カート内商品の個数更新
						// 商品画面出力用にセッションで管理中の在庫を更新（"LIST"の要素：List<Product>）
						item.getProduct().setStock(newStock);
					}
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