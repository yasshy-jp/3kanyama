<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="header.jsp" %>
<!------------------------------------------------------- TOP ---------------------------------------------------------->
        <div id="top-wrapper">
            <div class="container">
                <h1>愛媛の四季をご家庭へ</h1>
                <h2>Ehime's four seasons to your home.</h2>
                <p>みかん山白岩は、安全・安心・減農薬栽培と健康志向にこだわる農園です。</p>
                <p>年間をとおして様々な旬な野菜や果物を産地直送でお届けいたします。</p>
                <div class="btn-wrapper">
                    <p class="official">公式SNSはこちら</p>
                    <a href="business/" class="btn twitter">
                        <i class="fab fa-twitter"></i>
                    </a>
                    <a href="${pageContext.request.contextPath}/business/" class="btn line">
                        <i class="fab fa-line"></i>
                    </a>
                    <a href="https://www.instagram.com/yasshy_jp" class="btn instagram">
                        <i class="fab fa-instagram"></i>
                    </a>
                </div>
            </div>
        </div>
<!------------------------------------------------------ ABOUT --------------------------------------------------------->
        <div id="about-wrapper">
            <div class="container">
                <div class="heading">
                  <h2>ABOUT</h2>
                  <p class="translation">私たちについて</p>
                  <p class="digest">当園は来島瀬戸からほど近い小さな港町の山手、白岩地区で1930年代に柑橘栽培から始まりました。</p>
				  <p class="digest">温暖な気候と瀬戸内のミネラル豊富な潮風と雨風を受けた土壌は、美味しい野菜や果物を育んでくれます。</p>
              	</div>

                <h3>- Farm data -</h3>
                <div id="farmData">
                	<div class="farmPic">
	                    <img src="images/namikataView.jpg" alt="FarmPic" width="430" height="330">
                	</div>
                	<div class="voidFarmdata"></div>        	
                	<div class="farmInfo">
                        <p>
	                       	季節に応じて様々な野菜やみかんを主とした果物を栽培しており、四季折々の恵みが楽しめます。
							栽培面積の約半分がみかん畑で、畑上段からは波方地区の街並みと来島瀬戸の島々やしまなみ海道や行き交う船の景色も楽しめます。
		                </p>
                        <table>
                            <tr>
                                <th>農園名</th>
	                            <td>みかん山白岩</td>
                            </tr>
                            <tr>
                            	<th>所在地</th>
	                            <td>愛媛県今治市波方町白岩地区</td>
                            </tr>
                            <tr>
                                <th>営業時間</th>
	                            <td>8:30-15:00</td>
                            </tr>
                            <tr>
                                <th>定休日</th>
	                            <td>月、土、日、祝日、その他</td>
                            </tr>
                            <tr>
	                            <th>備 考</th>
	                            <td>定休日はSNSにて告知</td>
	                        </tr>
                        </table>
                    </div>
                </div>
                <a href="#access-wrapper" class="btn btnBig">Access <i class="fas fa-map-marker-alt"></i></a>
            </div>
        </div>
<!----------------------------------------------------- Product -------------------------------------------------------->
        <div id="product-wrapper">
            <div class="container">
				<div class="heading">
	            	<h2>Product</h2>
	                <p class="translation">栽培製品</p>
	                <p class="digest">主に4品目について様々な品種を栽培しています。</p>
	                <p class="digest">各写真より品目毎にオンラインショップで商品をご覧いただけます。</p>
	            </div>                
                <div class="items">
                    <div class="item">
                        <div class="item-icon">
                        	<a href="shopping/Product.action?category=1">
                          		<img src="images/orange.jpg" alt="Mikan" width="200" height="200">
                          		<p>みかん</p>
                        	</a> 
                        </div>
                        <p class="txt-contents">
                        	温州みかん、伊予柑、八朔、ネーブル、はるみ、せとか、紅まどんな、甘平
                        </p>
                    </div>
                    <div class="item">
                        <div class="item-icon">
                        	<a href="shopping/Product.action?category=2">
                          		<img src="images/vegetable.jpg" alt="Yasai" width="200" height="200">
                          		<p>野菜</p>
                          	</a>
                        </div>
                        <p class="txt-contents">
                        	キューリ、カボチャ、じゃがいも、玉ねぎ、オクラ、人参、えんどう豆、茄子、かぶら、ピーマン、トマト,etc.
                        </p>
                    </div>
                    <div class="item">
                        <div class="item-icon">
                        	<a href="shopping/Product.action?category=3">
                          		<img src="images/fruits.jpg" alt="Kudamono" width="200" height="200">
                          		<p>果物</p>
                          	</a>
                        </div>
                        <p class="txt-contents">
	                        柿、びわ、いちじく、りんご、すもも
                        </p>
                    </div>
                    <div class="item">
                        <div class="item-icon">
                        	<a href="shopping/Product.action?category=4">
                          		<img src="images/tea.jpg" alt="Ocha" width="200" height="200">
                          		<p>お茶</p>
                          	</a>
                        </div>
                        <p class="txt-contents">
                        	げんみょう茶
                        </p>
                    </div>
                </div>
                <a href="shopping/Product.action" class="btn btnBig">Shop <i class="fas fa-shopping-bag"></i></a>
            </div>
        </div>
<!----------------------------------------------------- GARRERY -------------------------------------------------------->
        <section class="fixed">
            <div class="garrery-wrapper">
                <div class="container">
                    <div class="void_garrery"></div>
                </div>
            </div>
        </section>
<!------------------------------------------------------ ACCSEE -------------------------------------------------------->
        <section class="fixed">
            <div id="access-wrapper">
                <div class="container">
                    <div class="heading">
                        <h2>ACCESS</h2>
                        <p class="translation">アクセス</p>
                        <p class="digest">
                        	<i class="fas fa-car-alt"></i> JR波止浜駅より車で約15分<br>
                        	<i class="fas fa-car-alt"></i> しまなみ海道今治北ICより車で約20分<br>
                        	愛媛本土最北端。三方を海に囲まれた今治市波方町。
                        </p>
                    </div>
           		<a href="contact/" class="btn btnBig">Contact <i class="fas fa-envelope"></i></a>	
                </div>
                <iframe src="https://www.google.com/maps/embed?pb=!1m14!1m12!1m3!1d424.14620088375415!2d132.94992498643538!3d34.1224115944463!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f163.0!5e0!3m2!1sja!2sjp!4v1664375210891!5m2!1sja!2sjp" width="100%" height="450" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
            </div>
        </section>
<!----------------------------------------------------- FOOTER --------------------------------------------------------->        
<%@include file="footer.jsp" %>