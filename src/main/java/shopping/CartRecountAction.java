package shopping;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Item;
import tool.Action;
// カート内での数量変更に伴う各種合計データの更新
public class CartRecountAction extends Action {
	
	@SuppressWarnings("unchecked")
	public String execute(
			HttpServletRequest request, HttpServletResponse response
		) throws Exception {
		
		HttpSession session = request.getSession();
		// 数量変更する商品のIDを取得
		int id = Integer.parseInt(request.getParameter("id"));
		// 数量変更する商品の変更数量を取得
		int recount = Integer.parseInt(request.getParameter("recount"));
		// 商品(項目Bean)を格納するカート(List)をセッション属性から取得
		List<Item> cart = (List<Item>)session.getAttribute("CART");
		// 現在の合計金額と個数をセッション属性から取得
		int totalPrice = (int)session.getAttribute("TOTALPRICE");
		int totalCount = (int)session.getAttribute("TOTALCOUNT");
		
		for (Item item : cart) {
			if (item.getProduct().getId() == id) {
				// 数量増加時：増加分を加算して個数を更新
				if(item.getCount() < recount) {
					totalPrice += item.getProduct().getPrice() * (recount - item.getCount());
					totalCount += (recount-item.getCount());
					item.setCount(recount);
				// 数量減少時：減少分を減算して個数を更新
				}else if(item.getCount() > recount){				
					totalPrice -= item.getProduct().getPrice() * (item.getCount() - recount);
					totalCount -= item.getCount() - recount;
					item.setCount(recount);
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