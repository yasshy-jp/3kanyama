package shopping;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.Product;
import dao.ProductSearchDAO;
import tool.Action;
// 商品一覧ページのレスポンスに必要なデータ取得
public class ProductAction extends Action {
	public String execute(HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		int category = 0;
		
		// 品目検索（"category" = 1〜4）
		if (request.getParameter("category") != null) {
			category = Integer.parseInt(request.getParameter("category"));
		}
		// キーワード検索
		String keyword = request.getParameter("keyword");
		
		// 品目＆キーワード共にデフォルト値の場合に全商品を表示さすためのパラメータを設定
		if (category == 0 && keyword == null) keyword = "";
		
		ProductSearchDAO dao = new ProductSearchDAO();
		List<Product> list=dao.search(category, keyword);

		// 検索結果の保持で買い物カゴなどページ遷移後に買い物の続きを可能にする
		session.setAttribute("LIST", list);
		session.setAttribute("KEYWORD", keyword);
		session.setAttribute("CATEGORY", category);

		return "product.jsp";
		
		}
}
