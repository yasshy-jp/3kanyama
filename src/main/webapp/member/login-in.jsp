<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@include file="../header.jsp" %>

        <div id="login-wrapper">
            <div class="container">
                <div class="heading">
                	<h2>Member Login</h2>
					<p class="translation">会員ログイン</p>
					<p class="translation_notes"><i class="fas fa-lock"></i>ベータ版のため試験運用中</p>
                	<form action="Login.action" method="post">
						<p>ログイン名</p><input type="text" name="login" class="text">
						<p>パスワード</p><input type="password" name="password" class="password">
						<p><input type="submit" class="btn btnBig login" value="ログイン"></p>
					</form>
					<p><a href="register.jsp">新規会員登録はこちら</a></p>
					<p><a href="../business/">パスワードを忘れた方はこちら</a></p>
					<br>
					<p><a href="../owner/login-owner.jsp">オーナーはこちら</a></p>
              	</div>
            </div>
        </div>
        
<%@include file="../footer.jsp" %>s