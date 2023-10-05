package shopping;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Item;
import bean.Member;
import jp.pay.Payjp;
import jp.pay.model.Card;
import jp.pay.model.Customer;
import tool.Action;

public class PreviewAction extends Action {
	@SuppressWarnings("unchecked")
	public String execute(
		HttpServletRequest request, HttpServletResponse response
	) throws Exception {
		
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute("MEMBER");  // 顧客オブジェクト
		// ログイン確認
		if (member == null) {
			return "preview-error-login.jsp";
		}
		// カートの確認
		List<Item> cart = (List<Item>)session.getAttribute("CART");
		if (cart == null || cart.size() == 0) {
			return "preview-error-cart.jsp";
		}
		// 決裁情報登録状況の確認
		String cusId = member.getCustomer_id();
		// 等しくない時（PAY.JP顧客登録者）にtrue
		if (!(cusId.equals("N/A"))) {
			Payjp.apiKey = "sk_test_5377902ef3aa9ca1ce2e73b7";
			Customer customer = Customer.retrieve(cusId);  //PAY.JPの顧客インスタンス
			Card card = customer.getCards().retrieve(member.getCard_id());
			String last4 = card.getLast4();
			String brand = card.getBrand();
			session.setAttribute("LAST4", last4);
			session.setAttribute("BRAND", brand);
			return "purchase-in_member.jsp";
		}

		return "purchase-in.jsp";
	}
}
