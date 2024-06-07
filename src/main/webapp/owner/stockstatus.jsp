<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../header.jsp" %>

		<div id="cart-wrapper">
			<div class="container">
				<div class="heading">
					<h2>Stock Status</h2>
					<p class="translation">在庫状況</p>
					<a href = "login-out_owner.jsp">オーナーHomeへ</a>
				</div>
				<div class="cartArea">
					<div class="cartArea-right">
						<%-- JSTLのCoreタグによる繰り返し。
						属性に保存されたリスト等のコレクションや配列を繰り返し対象に指定 --%>
						<c:forEach var = "stock" items = "${STOCKLIST}">
							<table>
								<tr>
									<td>${stock.id}</td>
									<td>${stock.kategoryName}</td>
									<td>${stock.name}</td>
									<td>${stock.price}</td>
									<td>${stock.stock}</td>
									<td>
										<form action = "StockAdd.action?id=${stock.id}" method = "post">
											<input type = "text" name = "stadd">
											<input type = "submit" value = "追加">
										</form>
									</td>
								</tr>
							</table>
						</c:forEach>
					</div>
				</div>
				<br>
				<br>
				<hr>
			</div>
		</div>
<%@include file="../footer.jsp" %>