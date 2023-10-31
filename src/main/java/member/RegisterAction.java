package member;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Member;
import dao.MemberRegisterDAO;
import tool.Action;
/***** 新規会員登録機能 *****/
public class RegisterAction extends Action {
	public String execute(
			HttpServletRequest request, HttpServletResponse response
		) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
			
		String login = request.getParameter("login"); // ログインID
		String password = request.getParameter("password"); // パスワード
		String password2 = request.getParameter("password2"); // 確認用パスワード
		String simei = request.getParameter("simei"); // 氏名
		String furigana = request.getParameter("furigana"); // フリガナ
		String mail = request.getParameter("mail"); //Mail
		String tel = request.getParameter("tel"); //Tel
		String postcode = request.getParameter("postcode"); //郵便番号
		String prefecture = request.getParameter("prefecture"); //都道府県
		String city = request.getParameter("city"); //市区町村
		String address = request.getParameter("address"); //番地,ビル名
		
		Member member = new Member();
		member.setLogin(login);
		member.setPassword(password);
		member.setSimei(simei);
		member.setFurigana(furigana);
		member.setMail(mail);
		member.setTel(tel);
		member.setPostcode(postcode);
		member.setPrefecture(prefecture);
		member.setCity(city);
		member.setAddress(address);
		// セッションスコープへ仮登録情報キープ
		session.setAttribute("MEMBER", member);
		
		/*****パスワードのパターンマッチング*****/
		// パターンオブジェクト生成
		Pattern p = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,}");
		// パターンマッチングのオブジェクト生成
        Matcher m = p.matcher(password);
        // パスワード形式チェック
		if (!(m.matches())) {
			member.setPassword("");
			return "r️egister-formaterror.jsp";		
		}
		// 確認パスワード同値チェック
		if (!(password.equals(password2))) {
			member.setPassword("");
			return "r️egister-matcherror.jsp";
		}
		// 未入力チェック
		if (login.isEmpty() || simei.isEmpty() || mail.isEmpty() || tel.isEmpty() || postcode.isEmpty()
				|| prefecture.isEmpty() || city.isEmpty()|| address.isEmpty()) {
			return "r️egister-emptyerror.jsp";
		}
		
		MemberRegisterDAO daoMem = new MemberRegisterDAO();
		int line = daoMem.r️egister(member);
		
		if (line == 1) {
			// セッションスコープの登録情報削除
			session.removeAttribute("MEMBER");
			return "r️egister-success.jsp";
		}
		return "r️egister-error.jsp";
	}
	
}
