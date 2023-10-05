package shopping;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Product;
import dao.ProductDAO;
import tool.Action;
// オンラインショップ商品一覧ページの生成
public class ProductAction extends Action {
	public String execute(
			HttpServletRequest request, HttpServletResponse response
		) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		int category = 0;
		
		String keyword = request.getParameter("keyword");
		// 品目検索のリクエスト（"category" = 1〜4）が来た際は、プリミティブ型に変換
		if (request.getParameter("category") != null) {
			category = Integer.parseInt(request.getParameter("category"));
		}
		
		// キーワードと品目ともにデフォルト値の場合に全商品を表示さすための設定
		if (keyword == null && category == 0) keyword = "";
		
		ProductDAO dao = new ProductDAO();
		List<Product> list=dao.search(keyword, category);

		// 検索結果の保持で買い物カゴなどページ遷移後に買い物の続きを可能にする
		session.setAttribute("LIST", list);
		session.setAttribute("KEYWORD", keyword);
		session.setAttribute("CATEGORY", category);

		return "product.jsp";
		
		}
}
