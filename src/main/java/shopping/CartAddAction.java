package shopping;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.Item;
import bean.Product;
import dao.ProductStockRegisterDAO;
import tool.Action;
// カートへの商品追加および各種合計データの計算
public class CartAddAction extends Action {
	@SuppressWarnings("unchecked")
	public String execute(HttpServletRequest request) throws Exception {
		
		// 各種情報取得と設定
		HttpSession session = request.getSession();
		List<Product> list = (List<Product>)session.getAttribute("LIST");  //商品リスト
		List<Item> cart = (List<Item>)session.getAttribute("CART");  // 商品カート
		int id = Integer.parseInt(request.getParameter("id"));  // 商品ID
		int addQuantity = Integer.parseInt(request.getParameter("addQuantity"));  // 追加商品の個数
		int totalPrice = 0; // 合計金額
		int totalCount = 0; // 合計個数
		int totalPrice_taxIn = 0; //税込み合計金額
		String newItemAdd_indicator = "on";  // onはカート内に既存しない種類の商品追加を示す指標
					
		if (cart == null) {
			cart = new ArrayList<Item>();
			session.setAttribute("CART", cart);
		}
		
		// カート内に追加商品と同種商品が存在する場合
		for (Item item : cart) {
			if (item.getProduct().getId() == id) {
				
				// 購入商品の個数更新
				item.setCount(item.getCount() + addQuantity);
				newItemAdd_indicator = "off";
				
				// 商品DBの在庫更新
				int stock = item.getProduct().getStock() - addQuantity;
				ProductStockRegisterDAO psrdao = new ProductStockRegisterDAO();
				int line = psrdao.r️egister(id, stock);
				if (line != 1) return "stock-register-error.jsp";
				
				// セッションに保存中の商品（"LIST"の要素）の在庫更新
				item.getProduct().setStock(stock);
				// 動作確認用コード
				System.out.println("同種商品「" + item.getProduct().getName() + 
						"」の追加。在庫は「" + item.getProduct().getStock() + "個」に減少。");
				break;
			}
		}
		
		// カート内に追加商品が存在していない場合（新規追加）
		if(newItemAdd_indicator == "on") {
			for (Product p : list) {
				if (p.getId() == id) {
					Item item = new Item();
					item.setProduct(p);
					item.setCount(addQuantity);
					
					// 商品DBの在庫更新
					int stock = p.getStock() - addQuantity;
					ProductStockRegisterDAO psrdao = new ProductStockRegisterDAO();
					int line = psrdao.r️egister(id, stock);
					if (line != 1) return "stock-register-error.jsp";
					
					// セッションに保存中の商品（"LIST"の要素）の在庫更新
					p.setStock(stock);
					// 動作確認用コード
					System.out.println("新規商品「" + p.getName() + "」"
							+ "の追加。在庫は「" + p.getStock() + "個」に減少。");
					
					cart.add(item);
					break;
			    }
     		}
		}
		
		// カート内の合計個数と金額の計算	
		for (Item item : cart) {
			totalPrice += item.getProduct().getPrice() * item.getCount();
			totalCount += item.getCount();
		}
		
		// 計算結果のセッションスコープへの格納
		totalPrice_taxIn = (int)(totalPrice * 1.1);
		session.setAttribute("TOTALPRICE_TAXIN", totalPrice_taxIn);
		session.setAttribute("TOTALPRICE", totalPrice);
		session.setAttribute("TOTALCOUNT", totalCount);
		return "cart.jsp";	
	}
}
