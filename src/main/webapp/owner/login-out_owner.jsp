<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../header.jsp" %>
		
    <div id="login-wrapper">
        <div class="container">
            <div class="heading">
             	<c:if test="${OWNER.name!=null}">
	             	<h2>Hello! ${OWNER.name}さん。</h2>
         		</c:if>
         		<br>
         		<a href = "OrderStatus.action">受注状況へ</a>
         		<br>
         		<br>
         		<a href = "StockStatus.action">在庫状況へ</a>
          	</div>
        </div>
    </div>
        
<%@include file="../footer.jsp" %>