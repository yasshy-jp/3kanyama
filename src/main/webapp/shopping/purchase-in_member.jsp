<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../header.jsp" %>

		<div id="purchase-wrapper">
			<div class="container">
				<div class="heading">
					<h2>購入手続き</h2>
					<p class="translation">Purchase</p>
					<p class="translation_notes"><i class="fas fa-lock"></i>ベータ版のため実販売停止中</p>
				</div>
				<div class="purchaseArea">
					<div class="purchase-left">
						<p class="total">合計${TOTALCOUNT}個（${CART.size()}種類）</p>
						<p>
							<strong class="totalPrice">¥${TOTALPRICE_TAXIN}</strong>
							<span class="tax">（内消費税¥${TOTALPRICE_TAXIN - TOTALPRICE}）</span>
						</p>
						<hr>
						<p class="pay">お支払い方法</p>
						<p>
							<strong class="cardbrand">${BRAND}</strong>
							<span class="cardnumber">（下4桁 ${LAST4}）</span>
						</p>
						<hr>
						<form action="Purchase.action?price=${TOTALPRICE_TAXIN}" method="post">	
							<input type="submit" value="支払いを確定" class="btn btnBig c️onfirm">
						</form>
					</div>
					<div class="purchase-right">
						<table>
							<c:forEach var="item" items="${CART}">
								<tr>
									<td class="line1">商品${item.product.id}</td>
									<td class="line2"><img src="image/${item.product.id}.jpg" height="96"></td>
									<td class="line3">${item.product.name}</td>
									<td class="line4">${item.product.price}円</td>
									<td class="line5">数量:${item.count}</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>	
			</div>
		</div>

<%@include file="../footer.jsp" %>