package shopping;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.Item;
import dao.ProductStockRegisterDAO;
import tool.Action;
// カート内での数量変更に伴う各種合計データの更新
public class CartRecountAction extends Action {
	@SuppressWarnings("unchecked")
	public String execute(HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		// 数量変更する商品IDを取得
		int id = Integer.parseInt(request.getParameter("id"));
		// 数量変更する商品数量を取得
		int recount = Integer.parseInt(request.getParameter("recount"));
		// 商品を格納するカートの取得
		List<Item> cart = (List<Item>)session.getAttribute("CART");
		// 現在の合計金額と個数の取得
		int totalPrice = (int)session.getAttribute("TOTALPRICE");
		int totalCount = (int)session.getAttribute("TOTALCOUNT");
		
		for (Item item : cart) {
			if (item.getProduct().getId() == id) {
				// 数量増加時
				if(item.getCount() < recount) {
					int incrementCount = recount - item.getCount();  // 増加個数
					totalPrice += item.getProduct().getPrice() * incrementCount;
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
				}else if(item.getCount() > recount){
					int decrementCount = item.getCount() - recount;  // 減少個数
					totalPrice -= item.getProduct().getPrice() * decrementCount;
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
					
				}else {
					return "cart.jsp";
				}
				
				// 税込み合計金額を再計算し、各種合計データをスコープへ再格納
				int totalPrice_taxIn = (int)(totalPrice * 1.1);
				session.setAttribute("TOTALPRICE_TAXIN", totalPrice_taxIn);
				session.setAttribute("TOTALPRICE", totalPrice);
				session.setAttribute("TOTALCOUNT", totalCount);
				break;
			}
		}
		return "cart.jsp";
	}
}