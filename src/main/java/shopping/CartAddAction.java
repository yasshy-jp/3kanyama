package shopping;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Item;
import bean.Product;
import tool.Action;
// カートの準備とカートへの商品追加および各種合計データの計算
public class CartAddAction extends Action {
	
	@SuppressWarnings("unchecked")
	public String execute(
			HttpServletRequest request, HttpServletResponse response
		) throws Exception {
		
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		int id = Integer.parseInt(request.getParameter("id")); // 商品ID
		int count = Integer.parseInt(request.getParameter("count")); // 商品個数
		int totalPrice = 0; // 合計金額
		int totalCount = 0; // 合計個数
		int totalPrice_taxIn = 0; //税込み合計金額
		String newItemAdd_indicator = "on"; // 追加商品がカート内に既に存在するかどうかの指標("on"は新たな商品)
		
		// 商品(Bean)を格納するカート(List)をセッションスコープから取得
		List<Item> cart = (List<Item>)session.getAttribute("CART");
		if (cart == null) {
			cart = new ArrayList<Item>();
			session.setAttribute("CART", cart);
		}	
		// カート内に追加商品と同種商品が存在する場合は個数を更新し、newItemAdd_indicatorを"off"に設定
		for (Item item : cart) {
			if (item.getProduct().getId() == id) {
				item.setCount(item.getCount() + count);
				newItemAdd_indicator = "off";
				break;
			}
		}
		// カート内に同種商品が存在していない場合は新たに商品情報を追加
		if(newItemAdd_indicator == "on") {
			List<Product> list = (List<Product>)session.getAttribute("LIST");
			for (Product p : list) {
				if (p.getId() == id) {
					Item item = new Item();
					item.setProduct(p);
					item.setCount(count);
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
		return "cart.jsp";	
	}
}
