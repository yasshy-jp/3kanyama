<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../header.jsp" %>

		<div id="cart-wrapper">
			<div class="container">
				<div class="heading">
					<h2>CART</h2>
					<p class="translation">ショッピングカート</p>
					<p class="translation_notes"><i class="fas fa-lock"></i>ベータ版のため実販売停止中</p>
				</div>
				<div class="cartArea">
					<div class="cartArea-left">
						<c:choose>
							<c:when  test = "${CART.size()>0}">
								<div class="calculation">
									<p class="total">合計${TOTALCOUNT}個（${CART.size()}種類）</p>
									<p>
										<strong  class="totalPrice">¥${TOTALPRICE_TAXIN}</strong>
										<span class="tax">（内消費税¥${TOTALPRICE_TAXIN - TOTALPRICE}）</span>
									</p>
								</div>
								<div class="btn-regi_or_con">
									<p class="choice"><a href="Preview.action" class="btn btnBig register">レジに進む</a></p>
									<p class="choice"><a href="Product.action" class="btn btnBig continue">買い物を続ける</a></p>
								</div>
							</c:when>
							<c:otherwise>
								<p>カートに商品がありません。</p>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="cartArea-right">
						<table>
							<c:forEach var = "item" items = "${CART}">
								<tr>
									<td class="line1">商品${item.product.id}</td>
									<td class="line2"><img src = "image/${item.product.id}.jpg" height = "96"></td>
									<td class="line3">${item.product.name}</td>
									<td class="line4">${item.product.price}円</td>
									<%-- 表のセル内にフォームによるリクエストパラメータを設置 --%>
									<td class="line5">
										<form action = "CartRecount.action?id=${item.product.id}" method = "post">
											数量:
											<select name = "recount" class="recount">
											<%-- 三項演算子(p324)とSelected属性により、ページ読み込み時点での選択個数を予め表示 --%>
												<c:forEach var = "i" begin = "1" end = "20">
													<option value = "${i}" ${item.count == i ? "selected = \"selected\"" : ""}>${i}</option>
												</c:forEach>
											</select>
											<input type = "submit" class="update" value = "更新">
										</form>
									</td>
									<td><a href = "CartRemove.action?id=${item.product.id}">削除</a></td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>	
			</div>
		</div>
<%@include file="../footer.jsp" %>