<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">

    <head>
        <meta charset="UTF-8">
        <%-- レスポンシブデザインの設定 --%>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="みかん山白岩は愛媛県今治市しまなみ海道の起点近くで野菜や果物を栽培している農園です。">
        <title>愛媛県今治市【みかん＆農園】みかん山白岩</title>
        <%-- フリーアイコン読み込み設定。＊読込み位置によりdisplay:プロパティでの要素の表示に影響あり --%>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/css/normalize.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/css/style.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/css/responsive.css">
        <%-- 使用するライブラリ jQuery, stickfill.jsの読込み ＊jqを利用するFileより前で読み込むこと --%>
        <script src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/stickyfill/2.1.0/stickyfill.min.js"></script>
        <script src="${pageContext.request.contextPath}/common/js/app2.js"></script>
    </head>

    <body>
        <header>
            <div class="header-left">
            	<a href="${pageContext.request.contextPath}/#">
                    <span class="salonName"><i class="fas fa-leaf"></i>みかん山白岩</span>
            	</a>    
            </div>

            <%-- スマホ用ハンバーガーメニュー --%>
            <dl id="humburger_menu">
                <dt><div class="openbtn"><span></span><span></span><span></span></div></dt>
                <dd><a href="${pageContext.request.contextPath}/#">Home</a></dd>
                <dd><a href="${pageContext.request.contextPath}/#about-wrapper">About</a></dd>
                <dd><a href="${pageContext.request.contextPath}/#product-wrapper">Product</a></dd>
                <dd><a href="${pageContext.request.contextPath}/shopping/Product.action">Shop</a></dd>
                <dd><a href="${pageContext.request.contextPath}/member/login-in.jsp">Login</a></dd>
                <dd><a href="${pageContext.request.contextPath}/member/logout-in.jsp">Logout</a></dd>
				<dd><a href="${pageContext.request.contextPath}/shopping/cart.jsp" id="iconHead"><i class="fas fa-shopping-cart"></i></a></dd>
				<dd><a href="${pageContext.request.contextPath}/contact/" id="iconHead"><i class="fas fa-envelope"></i></a></dd>
            </dl>
                
            <div class="header-right">
            	<a href="${pageContext.request.contextPath}/#">Home</a>
                <a href="${pageContext.request.contextPath}/#about-wrapper">About</a>
                <a href="${pageContext.request.contextPath}/#product-wrapper">Product</a>
                <a href="${pageContext.request.contextPath}/shopping/Product.action">Shop</a>
                <a href="${pageContext.request.contextPath}/member/login-in.jsp">Login</a>
                <a href="${pageContext.request.contextPath}/member/logout-in.jsp">Logout</a>
                <a href="${pageContext.request.contextPath}/shopping/cart.jsp" id="iconHead"><i class="fas fa-shopping-cart"></i></a>
                <a href="${pageContext.request.contextPath}/contact/" id="iconHead"><i class="fas fa-envelope"></i></a>
            </div>
        </header>