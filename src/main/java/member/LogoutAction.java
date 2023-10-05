package member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.Action;

public class LogoutAction extends Action {
	public String execute(
			HttpServletRequest request, HttpServletResponse response
		) throws Exception {
		
			HttpSession session=request.getSession();
			// 会員アカウント
			if (session.getAttribute("MEMBER")!=null) {
				// 設定された全てのセッション属性を削除。
				session.invalidate();
				return "logout-out.jsp";
			// オーナーアカウント
			} else if (session.getAttribute("OWNER")!=null) {
				session.invalidate();
				return "logout-out.jsp";
		}
		
		return "logout-error.jsp";
		}
}
