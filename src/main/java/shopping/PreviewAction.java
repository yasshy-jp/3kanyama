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
// PAY.JPの決済情報登録状況に応じた確認画面のレスポンス
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
		// 顧客ID所有者は決裁情報登録者
		String cusId = member.getCustomer_id();
		// 決済情報登録者向け画面 (等しくない時にtrue）
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
		// 決済情報未登録者向け画面
		return "purchase-in.jsp";
	}
}
