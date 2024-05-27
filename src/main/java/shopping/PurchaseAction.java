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
		String registerCard = request.getParameter("registerCard");  // 顧客情報（カード）登録希望の有無
		List<Item> cart = (List<Item>)session.getAttribute("CART");  // カートオブジェクト
		Member member = (Member)session.getAttribute("MEMBER");  // 会員オブジェクト
		
		/************* PAY.JPのAPIで支払い処理 *************/
		Payjp.apiKey = "********************************";
		Customer customer = null;
		Charge charge = null;
		Card card = null;
		String property = null; // 与信枠の確保用パラメータ
		String value = null; // 与信枠の確保用パラメータ
		
		/*** 与信枠の確保に必要なパラメータ設定 *************************************************************************/
		// PAY.JP顧客（決済情報）登録者
		if (!(member.getCustomer_id().equals("N/A"))) { 
			property = "customer";
			value = member.getCustomer_id();
		// PAY.JP顧客（決済情報）新規登録者
		} else if (registerCard != null) {
			/* ↑ value属性に値がないチェックボックスにチェックした時のリクエストパラメータは"on"
			   ↓ PAY.JP顧客作成。マップ形式で顧客オブジェクトの各プロパティを設定。詳細はPAY.JP API参照。
				 https://pay.jp/docs/api/?java#%E9%A1%A7%E5%AE%A2%E3%82%92%E4%BD%9C%E6%88%90 */
			Map<String, Object> customerParams = new HashMap<String, Object>();
			customerParams.put("card", payjpToken); // トークンを指定することで顧客にカード情報を登録
			try {
				customer = Customer.create(customerParams);
				System.out.println(customer); // 作成された顧客情報をコンソール出力
			} catch (Exception e) {
	            e.printStackTrace();
			}
			property = "customer";
			value = customer.getId();
		// PAY.JP顧客（決済情報）登録なしで購入
		} else {
			property = "card";
			value = payjpToken; // 1回限り有効のトークン払い
		}
		
		/*** (1) 与信枠の確保 ****************************************************************************************/
		// ↓ マップ形式で支払いオブジェクトの各種プロパティ値を設定。詳細はPAY.JP API参照。
		// https://pay.jp/docs/api/?java#%E6%94%AF%E6%89%95%E3%81%84%E3%82%92%E4%BD%9C%E6%88%90
		Map<String, Object> chargeParams = new HashMap<String, Object>();
		chargeParams.put(property, value); // パラメータで支払い方法設定（顧客IDに紐づく登録済みカード or 1回限り有効のトークン）
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
		// PAY.JP顧客（決済情報）新規登録者は、みかん山白岩の会員DBへ顧客＆カードIDを追加（次回よりカード情報入力不要で決済可）
		if (registerCard != null) {
			card = charge.getCard();
  			MemberAddRegisterDAO daoMem = new MemberAddRegisterDAO();
			int line = daoMem.r️egister(customer, card, member);
			if (line != 1) {
				return "/member/r️egister-iderror.jsp";
			}
		}
		
		/*** (2) 購入明細DBへ追加 ************************************************************************************/
		PurchaseDAO daoPur = new PurchaseDAO();
		if (cart == null || !daoPur.insert(charge, cart)) {
			return "purchase-insert-error.jsp";
		}
		
		/*** (3) 支払い確定 ******************************************************************************************/
		String ch_id = charge.getId(); // 課金IDを取得
		try {
			Charge ch = Charge.retrieve(ch_id);
			System.out.println(ch.capture()); // 確定処理と結果（支払いオブジェクト）のコンソール出力
        } catch (Exception e) {
        	e.printStackTrace();
        	return "credit-confirm_error.jsp";
        }
		
		/*** (4) 売上DBへ追加 ****************************************************************************************/
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
