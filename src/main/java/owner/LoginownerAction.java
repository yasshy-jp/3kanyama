package owner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.Owner;
import dao.OwnerDAO;
import tool.Action;

public class LoginownerAction extends Action {
	public String execute(HttpServletRequest request) throws Exception {

			HttpSession session=request.getSession();

			String login=request.getParameter("login");
			String password=request.getParameter("password");

			OwnerDAO daoOwn=new OwnerDAO();
			Owner owner=daoOwn.search(login, password);
			
			if (owner!=null) {
				session.setAttribute("OWNER", owner);
				return "login-out_owner.jsp";
			}
			return "owner-error-login.jsp";
		}
}
