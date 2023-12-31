package shopping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Item;
import bean.Member;
import dao.MemberAddRegisterDAO;
import dao.ProceedsDAO;
import dao.PurchaseDAO;
import jp.pay.Payjp;
import jp.pay.model.Card;
import jp.pay.model.Charge;
import jp.pay.model.Customer;
import tool.Action;

public class PurchaseAction extends Action {
	@SuppressWarnings("unchecked")
	public String execute(
		HttpServletRequest request, HttpServletResponse response
	) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		
		String price = request.getParameter("price");  // 税込み合計金額
		String payjpToken = request.getParameter("payjp-token");  // カード情報入力後、生成されたトークン
		String registerCard = request.getParameter("registerCard");  // 顧客情報（カード）登録の有無
		List<Item> cart = (List<Item>)session.getAttribute("CART");  // カートオブジェクト
		Member member = (Member)session.getAttribute("MEMBER");  // 顧客オブジェクト
		
		/************* PAY.JPのAPIで支払い処理 *************/
		Payjp.apiKey = "sk_test_5377902ef3aa9ca1ce2e73b7";
		Customer customer = null;
		Charge charge = null;
		Card card = null;
		String property = null; // 与信枠の確保用パラメータ
		String value = null; // 与信枠の確保用パラメータ
		
		/*--- PAY.JP 顧客登録状況に応じた与信枠の確保に必要なパラメータ設定 ---*/
		// PAY.JP 顧客登録済み者の設定
		if (!(member.getCustomer_id().equals("N/A"))) { 
			property = "customer";
			value = member.getCustomer_id();
		// PAY.JP「新規」顧客登録者の設定
		} else if (registerCard != null) {
			// ↑ value属性に値がないチェックボックスにチェックした時のリクエストパラメータ値は"on"
			// ↓ 顧客の作成。マップ形式で顧客オブジェクトの各種プロパティ値を設定。詳細はPAY.JP API参照。
			// https://pay.jp/docs/api/?java#%E9%A1%A7%E5%AE%A2%E3%82%92%E4%BD%9C%E6%88%90
			Map<String, Object> customerParams = new HashMap<String, Object>();
			customerParams.put("card", payjpToken); // 顧客にトークンを紐付けることで支払い時のカード情報入力を排除
			try {
				customer = Customer.create(customerParams);
				System.out.println(customer); // 作成された顧客情報をコンソール出力
			} catch (Exception e) {
	            e.printStackTrace();
			}
			property = "customer";
			value = customer.getId();
		// PAY.JP 顧客登録しないで購入する場合の設定
		} else {
			property = "card";
			value = payjpToken; // 1回限り有効のトークン払い
		}
		
		/*** (1) 与信枠の確保 ***/
		// ↓ マップ形式で支払いオブジェクトの各種プロパティ値を設定。詳細はPAY.JP API参照。
		// https://pay.jp/docs/api/?java#%E6%94%AF%E6%89%95%E3%81%84%E3%82%92%E4%BD%9C%E6%88%90
		Map<String, Object> chargeParams = new HashMap<String, Object>();
		chargeParams.put(property, value); // 前項で取得したパラメータ値の設定
		chargeParams.put("amount", price);
		chargeParams.put("currency", "jpy");
		chargeParams.put("capture", "false"); // カードの認証と支払い額の確保のみ行う設定

		try {
			charge = Charge.create(chargeParams);
			System.out.println(charge); // 与信枠が確保された支払い情報をコンソール出力
        } catch (Exception e) {
        	e.printStackTrace();
        	return "credit_error.jsp";
        }
		// PAY.JP「新規」顧客登録者は、みかん山白岩の会員DBへ登録情報を追加（顧客ID＆カードID）
		if (registerCard != null) {
			card = charge.getCard();
  			MemberAddRegisterDAO daoMem = new MemberAddRegisterDAO();
			int line = daoMem.r️egister(customer, card, member);
			if (line != 1) {
				return "/member/r️egister-iderror.jsp";
			}
		}
		
		/*** (2) 購入明細DBへ追加 ***/
		PurchaseDAO daoPur = new PurchaseDAO();
		if (cart == null || !daoPur.insert(charge, cart)) {
			return "purchase-insert-error.jsp";
		}
		
		/*** ③ 支払い確定 ***/
		String ch_id = charge.getId(); // 課金IDを取得
		try {
			Charge ch = Charge.retrieve(ch_id);
			System.out.println(ch.capture()); // 確定処理と結果（支払いオブジェクト）のコンソール出力
        } catch (Exception e) {
        	e.printStackTrace();
        	return "credit-confirm_error.jsp";
        }
		
		/*** ④ 売上DBへ追加 ***/
		ProceedsDAO daoPro = new ProceedsDAO();
		boolean result = daoPro.insert(charge, member, price);
		if (!result) {
			return "proceeds-insert-error.jsp";
		}
		
		session.removeAttribute("CART");
		if (registerCard != null) {
			return "purchase-out_register.jsp";
		}
		return "purchase-out_member.jsp";
	}

}