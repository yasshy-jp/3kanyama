<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../header.jsp" %>

		<div id="cart-wrapper">
			<div class="container">
				<div class="heading">
					<h2>Order Status</h2>
					<p class="translation">受注状況</p>
					<a href = "login-out_owner.jsp">オーナーHomeへ</a>
				</div>
				<div class="cartArea">
					<div class="cartArea-right">
						<%-- JSTLのCoreタグによる繰り返し。
						属性に保存されたリスト等のコレクションや配列を繰り返し対象に指定 --%>
						<c:forEach var = "order" items = "${ORDERLIST}">
							<table>	
								<tr>
									<td>${order.date}</td>
									<td>${order.simei}</td>
									<td>${order.charge_id}</td>
									<td>${order.totalprice}</td>
									<td>${order.name}</td>
									<td>${order.price}</td>
									<td>${order.count}</td>
									<td><a href = "">削除</a></td> <%-- リンク先実装予定：Remove.action --%>
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