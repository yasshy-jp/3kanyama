<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../header.jsp"%>
<!---------------------------------------------------- SHOP(遷移先) ----------------------------------------------------->
<div id="shop-wrapper">
	<div class="container">
		<div class="heading">
			<h2>SHOP</h2>
			<p class="translation">オンラインショップ</p>
			<p class="translation_notes">
				<i class="fas fa-lock"></i>ベータ版のため実販売停止中
			</p>
			<!-- キーワード検索 -->
			<div class="searchText">
				<form action="Product.action" method="post">
					<input type="text" name="keyword" value="${KEYWORD}">
					<input type="submit" value="検索">
				</form>
			</div>
			<!-- 品目検索 -->
			<div class="searchItem">
				<form action="Product.action" method="post">
					<select name="category" required>
						<option value="" hidden>品目を選択</option>
						<option value=1 ${CATEGORY == 1 ? "selected = \"selected\"" : ""}>みかん</option>
						<option value=2 ${CATEGORY == 2 ? "selected = \"selected\"" : ""}>野菜</option>
						<option value=3 ${CATEGORY == 3 ? "selected = \"selected\"" : ""}>果物</option>
						<option value=4 ${CATEGORY == 4 ? "selected = \"selected\"" : ""}>お茶</option>
						<%-- ELによる三項演算子(p324)とリクエストパラメータ取得(p328)により、選択値を保持するselected属性を設定 --%>
					</select> <input type="submit" value="並べ替え">
				</form>
			</div>
			<!-- 商品一覧 -->
			<div class="allproduct">
				<form action="Product.action" method="post">
					<input type="submit" value="商品一覧">
				</form>
			</div>
		</div>
		<!-------------------------------------------------- Product List ------------------------------------------------------>
		<br>
		<div class="itemLists">
			<%-- var:リストから取り出した要素（オブジェクト）を格納する変数
				items:繰り返し対象のコレクションや配列（EL式は属性名だけで何れかのスコープに保存したオブジェクトが取得可能）--%>
			<c:forEach var="product" items="${LIST}">	
				<div class="itemForm">
					<table class="itemList">
						<tr>
							<td colspan="2">
								<a href="../business/" target="_blank" rel="noopener noreferrer" class="one"> 
									<%-- ${変数名.プロパティ名}でプロパティを取得（SBC P319,337）クラス名不要で取得できる --%>
									<img src="image/${product.id}.jpg" alt="itemPic" width=100%>
								</a>
							</td>
						</tr>
						<tr>
							<td>商品${product.id}</td>
							<td>${product.name}</td>
						</tr>
						<tr>
							<td>在庫</td>
							<td>
								<c:choose>
									<c:when test="${product.stock>0}">${product.stock}個</c:when>
									<c:otherwise>欠品中</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td>${product.price}円</td>
							<td>
								<c:choose>
									<%-- 在庫ありのUI --%>
									<c:when test="${product.stock>0}">
										<form action="CartAdd.action" method="post">
											<input type="hidden" name="id" value="${product.id}">
											<label for="addQuantity${product.id}">数量:</label>
											<select name="addQuantity" id="addQuantity${product.id}">
												<%-- 繰り返し回数の指定（SBC P341）--%>
												<c:forEach var="i" begin="1" end="20">
													<option value="${i}">${i}</option>
												</c:forEach>
											</select>
											<button type="submit">カートに追加</button>
										</form>
									</c:when>
									<%-- 在庫なしのUI --%>
									<c:otherwise>
										<div>
											<label for="addQuantity${product.id}">数量:</label>
											<select name="addQuantity" id="addQuantity${product.id}" disabled>
												<%-- 空白を示す文字エンティティ：&ensp; --%>
												<option value="0">0&ensp;</option>
											</select>
											<button type="button" disabled aria-disabled="true" title="欠品中">入荷待ち</button>
										</div>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</table>
				</div>
			</c:forEach>
		</div>
	</div>
</div>
<!------------------------------------------------------ FOOTER -------------------------------------------------------->
<%@include file="../footer.jsp"%>