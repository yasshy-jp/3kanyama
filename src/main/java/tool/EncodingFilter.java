package tool;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;

@WebFilter(urlPatterns = { "/*" })
public class EncodingFilter extends HttpFilter implements Filter {

	public void doFilter(
			ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//リクエスト＆レスポンスの文字エンコーディング等の設定
		request.setCharacterEncoding("UTF-8");
		//response.setContentType("text/html; charset=UTF-8");
		/* JSPファイルのPageディレクティブで指定するcontentType属性と同じ動作をするが、
		 * ファイル自体を読み込む際の文字エンコーディング指定を兼ねているためJSPでは省略できない。
		 */

		//System.out.println("フィルタの前処理");
		chain.doFilter(request, response);
		//System.out.println("フィルタの後処理");
	}

	public void init(FilterConfig filterConfig) {
	}

	public void destroy() {
	}

}
