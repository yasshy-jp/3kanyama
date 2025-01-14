<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header.jsp" %>

        <div id="login-wrapper">
            <div class="container">
                <div class="heading">
                	<h2>Owner Login</h2>
                	<br>
					<p class="translation"><a href = "Loginowner.action">オーナーHOMEへ</a></p>
                	<form action="Loginowner.action" method="post">
						<p>ログイン名</p><input type="text" name="login" class="text">
						<p>パスワード</p><input type="password" name="password" class="password">
						<p><input type="submit" class="btn btnBig login" value="ログイン"></p>
					</form>
              	</div>
            </div>
        </div>
        
<%@include file="../footer.jsp" %>