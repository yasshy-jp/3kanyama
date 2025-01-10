<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../header.jsp" %>
		
    <div id="login-wrapper">
        <div class="container">
            <div class="heading">
            	<%-- JSTLのCoreタグifと、EL式により、
            	Bean（会員）のプロパティ（氏名）がセッション属性へ保存されているか確認 --%>
             	<c:if test="${MEMBER.simei!=null}">
             		<h2>こんにちは、${MEMBER.simei}さん。</h2>
         		</c:if>
         		
         		    <br>
                	<br>
                	<br>
                	<br>
                	<br>
                	<br>
                	<br>
                	<br>
                	<br>
                	<br>
                	<br>
                	<br>
                	<br>
                	<br>
         		
          	</div>
        </div>
    </div>
        
<%@include file="../footer.jsp" %>