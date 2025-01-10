package shopping;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Item;
import bean.Product;
import dao.ProductStockRegisterDAO;
import tool.Action;
// カートの準備とカートへの商品追加および各種合計データの計算
public class CartAddAction extends Action {
	
	@SuppressWarnings("unchecked")
	public String execute(
			HttpServletRequest request, HttpServletResponse response
		) throws Exception {
		
		// 商品(Bean)を格納するカート(List)や検索商品リストをセッションスコープから取得
		HttpSession session = request.getSession();
		List<Product> list = (List<Product>)session.getAttribute("LIST");  //商品リスト
		List<Item> cart = (List<Item>)session.getAttribute("CART");
		
		// 追加商品等の情報取得と設定
		int id = Integer.parseInt(request.getParameter("id"));  // 商品ID
		int addQuantity = Integer.parseInt(request.getParameter("addQuantity"));  // カートへ追加の個数 
		int totalPrice = 0; // 合計金額
		int totalCount = 0; // 合計個数
		int totalPrice_taxIn = 0; //税込み合計金額
		String newItemAdd_indicator = "on";
		// 追加商品がカート内に既存するかどうかの指標(デフォルト値の"on"は既存しない新たな商品を追加することを示す)
				
		if (cart == null) {
			cart = new ArrayList<Item>();
		}
		
		// カート内に追加商品と同種商品が存在する場合は個数を更新し、newItemAdd_indicatorを"off"に設定
		for (Item item : cart) {
			if (item.getProduct().getId() == id) {
				item.setCount(item.getCount() + addQuantity);
				newItemAdd_indicator = "off";
				
				// 商品DBの在庫の更新
				int stock = item.getProduct().getStock() - addQuantity;
				ProductStockRegisterDAO psrdao = new ProductStockRegisterDAO();
				int line = psrdao.r️egister(id, stock);
				if (line != 1) {
					return "stock-register-error.jsp";
				}
				
				// 商品リストの在庫表示の更新（セッションスコープの商品在庫の更新）
				item.getProduct().setStock(stock);
				System.out.println("同種" + item.getProduct().getStock());
				
				
				break;
			}
		}
		
		// カート内に同種商品が存在していない場合は新たに商品情報を追加
		if(newItemAdd_indicator == "on") {
			for (Product p : list) {
				if (p.getId() == id) {
					Item item = new Item();
					item.setProduct(p);
					item.setCount(addQuantity);
					
					// 商品DBの在庫の更新
					int stock = p.getStock() - addQuantity;
					ProductStockRegisterDAO psrdao = new ProductStockRegisterDAO();
					int line = psrdao.r️egister(id, stock);
					if (line != 1) {
						return "stock-register-error.jsp";
					}
					
					// 商品リストの在庫表示の更新（セッションスコープの商品在庫の更新）
					p.setStock(stock);
					System.out.println("新規" + p.getStock());
					
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
		
		// 税込み合計金額計算と各種合計情報のセッションスコープへの格納
		totalPrice_taxIn = (int)(totalPrice * 1.1);
		session.setAttribute("TOTALPRICE_TAXIN", totalPrice_taxIn);
		session.setAttribute("TOTALPRICE", totalPrice);
		session.setAttribute("TOTALCOUNT", totalCount);
		session.setAttribute("LIST", list);
		session.setAttribute("CART", cart);
		return "cart.jsp";	
	}
}
