package member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Member;
import dao.MemberSearchDAO;
import tool.Action;

public class LoginAction extends Action {
	public String execute(
			HttpServletRequest request, HttpServletResponse response
		) throws Exception {
			
			request.setCharacterEncoding("UTF-8");
			HttpSession session=request.getSession();

			String login=request.getParameter("login");
			String password=request.getParameter("password");

			MemberSearchDAO daoMem=new MemberSearchDAO();
			Member member=daoMem.search(login, password);
			
			if (member!=null) {
				session.setAttribute("MEMBER", member);
				return "login-out.jsp";
			}
			
			return "login-error.jsp";
		}
}
