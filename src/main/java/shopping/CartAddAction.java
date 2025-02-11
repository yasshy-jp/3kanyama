package shopping;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.Item;
import bean.Product;
import dao.UpdateStockDAO;
import tool.Action;

// カートへ商品を追加するクラス
public class CartAddAction extends Action {
	@SuppressWarnings("unchecked")
	public String execute(HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		List<Product> list = (List<Product>) session.getAttribute("LIST"); //商品リスト
		List<Item> cart = (List<Item>) session.getAttribute("CART"); // 商品カート
		int id = Integer.parseInt(request.getParameter("id")); // 商品ID
		int addQuantity = Integer.parseInt(request.getParameter("addQuantity")); // 追加商品の個数
		int totalPrice_taxIn = 0; //合計金額（税込）
		int totalCount = 0; // 合計個数
		String newItemAdd_indicator = "on"; // onはカート内に既存しない種類の商品追加を示す指標

		if (cart == null) {
			cart = new ArrayList<Item>();
			session.setAttribute("CART", cart);
		}

		// カート内に追加商品と同種商品が存在する場合
		for (Item item : cart) {
			if (item.getProduct().getId() == id) {

				// 商品DBの在庫確認と減算（カートに追加時）
				UpdateStockDAO udsdao = new UpdateStockDAO();
				int line = udsdao.updateStock(id, addQuantity);
				if (line != 1)
					return "stockShortageError.jsp"; // 在庫不足

				// カート内商品の個数更新
				item.setCount(item.getCount() + addQuantity);
				newItemAdd_indicator = "off";

				// 商品一覧画面出力用にセッションで管理中の在庫を更新（"LIST"の要素：List<Product>）
				item.getProduct().setStock(item.getProduct().getStock() - addQuantity);
				// 動作確認用コード
				System.out.println("同種商品「" + item.getProduct().getName() +
						"」の追加。在庫は「" + item.getProduct().getStock() + "個」に減少。");
				break;
			}
		}

		// カート内に追加商品が存在していない場合（新規追加）
		if (newItemAdd_indicator == "on") {
			for (Product p : list) {
				if (p.getId() == id) {
					Item item = new Item();
					item.setProduct(p);
					item.setCount(addQuantity);

					// 商品DBの在庫確認と更新（カートに追加時）
					UpdateStockDAO udsdao = new UpdateStockDAO();
					int line = udsdao.updateStock(id, addQuantity);
					if (line != 1)
						return "stockShortageError.jsp"; // 在庫不足

					// 商品一覧画面出力用にセッションで管理中の在庫を更新（"LIST"の要素：List<Product>）
					p.setStock(p.getStock() - addQuantity);
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
			totalPrice_taxIn += (int) (item.getProduct().getPrice() * item.getCount() * 1.1);
			totalCount += item.getCount();
		}

		// 計算結果のセッションスコープへの格納
		session.setAttribute("TOTALPRICE_TAXIN", totalPrice_taxIn);
		session.setAttribute("TOTALCOUNT", totalCount);
		return "cart.jsp";
	}
}
