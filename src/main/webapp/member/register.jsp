<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header.jsp" %>

        <div id="register-wrapper">
            <div class="container">
                <div class="heading">
                	<h2>会員登録フォーム</h2>
				</div>	
				<div class="registerForm">
					<p>
                		送信いただく個人情報の取り扱いにつきましては、細心の注意を払っております。<br>
						お預かりしたお客様の情報は本人様へのお問い合わせ内容についてのご返答や情報のご提供の目的であり、<br>
						他の目的に使用することはございません。	詳しくは<a href="#">「プライバシーポリシー」</a>をご覧ください。<br>
						<span>＊</span>印の項目は必須となります。漏れなくご記入ください。英数字は半角での記入をお願いいたします。
					</p>
                	<form action="Register.action" method="post">
                		<table>
                            <tr>
                                <th><span>＊</span>ログインID</th>
	                            <td><p><input type="text" class="textbox" value="${MEMBER.getLogin()}" name="login"></p></td>
                            </tr>
                            <tr>
                                <th><span>＊</span>パスワード</th>
	                            <td>
	                            	<p>＊半角で英小文字/英大文字/数字を全て含み8文字以上
	                            		<input type="password" class="textbox" value="${MEMBER.getPassword()}" name="password">
	                            	</p>
	                            </td>
                            </tr>
                            <tr>
                                <th><span>＊</span>パスワード<br>（確認用）</th>
	                            <td>
	                            	<p>＊半角で英小文字/英大文字/数字を全て含み8文字以上
	                            		<input type="password" class="textbox" value="${MEMBER.getPassword()}" name="password2">
	                            	</p>
	                            </td>
                            </tr>
                            <tr>
                                <th><span>＊</span>お名前</th>
	                            <td><p><input type="text" class="textbox" value="${MEMBER.getSimei()}" name="simei"></p></td>
                            </tr>
                            <tr>
                                <th>フリガナ</th>
	                            <td><p><input type="text" class="textbox" value="${MEMBER.getFurigana()}" name="furigana"></p></td>
                            </tr>
                            <tr>
                                <th><span>＊</span>メールアドレス</th>
	                            <td><p><input type="text" class="textbox" value="${MEMBER.getMail()}" name="mail"></p></td>
                            </tr>
                            <tr>
                                <th><span>＊</span>電話番号</th>
	                            <td><p><input type="text" class="textbox" value="${MEMBER.getTel()}" name="tel"></p></td>
                            </tr>
                            <tr>
                                <th><span>＊</span>郵便番号</th>
	                            <td>
	                            	<p>
	                            		<input type="text" id="zipcode" class="textbox" value="${MEMBER.getPostcode()}" maxlength="8" name="postcode">
	                            		<button type="button" id="btncode">検索</button>
	                            	</p>
	                            </td>
                            </tr>
                            <tr>
                                <th><span>＊</span>都道府県</th>
	                            <td><p><input type="text" id="prefecture" class="textbox" value="${MEMBER.getPrefecture()}" name="prefecture"></p></td>
                            </tr>
                                                        <tr>
                                <th><span>＊</span>市区町村</th>
	                            <td><p><input type="text" id="city" class="textbox" value="${MEMBER.getCity()}" name="city"></p></td>
                            </tr>
                                                        <tr>
                                <th><span>＊</span>番地,ビル名</th>
	                            <td><p><input type="text" class="textbox" value="${MEMBER.getAddress()}" name="address"></p></td>
                            </tr>
                        </table>
						<input type="submit" class="btn btnBig register" value="登 録">
					</form>
				</div>
            </div>
        </div>

<%@include file="../footer.jsp" %>