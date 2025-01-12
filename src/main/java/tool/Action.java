package tool;

import javax.servlet.http.HttpServletRequest;

public abstract class Action {
	public abstract String execute(HttpServletRequest request) throws Exception;
}
