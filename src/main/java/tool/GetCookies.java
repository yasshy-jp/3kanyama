package tool;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/tool/getcookies")
public class GetCookies extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out=response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='UTF-8'>");
		out.println("<title>Get Cookies</title>");
		out.println("</head>");
		out.println("<body>");
		
		// クッキーの取得
		Cookie[] cookies=request.getCookies();
		
		/* 各クッキーの取り出しとクライアントサイドへの表示
		 * セッションを使うプログラムを実行した後ならば、
		 * セッションIDを保持するクッキーも表示されることがある。
		 */
		if (cookies!=null) {
			for (Cookie cookie : cookies) {
				String name=cookie.getName();
				String value=cookie.getValue();
				out.println("<p>"+name+" : "+value+"</p>");
			}
		} else {
			out.println("クッキーは存在しません");
		}

		out.println("</body>");
		out.println("</html>");
	}

}
