<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@include file="../header.jsp" %>

        <div class="top-wrapper_contact">
            <div class="container">
                <h2>お問い合わせフォーム</h2>
            </div>
        </div>

        <div class="contact-wrapper">
            <div class="container">
                <div class="heading">
	                <p class="translation_notes"><i class="fas fa-lock"></i>ベータ版のため試験運用中</p>
                    <p class="digest">下記フォームに必要事項をご記入の上、送信ボタンを押してください。</p>
                    <p>※プライバシポリシーを事前にご確認ください。</p>
                    <p class="btn_privacy">
                        <a href="../business/">
                            <span class="mgr-10">プライバシポリシー</span>＞
                        </a>
                    </p>
                </div>
                <form action="../business/" class="form" method="post">
                    <p>お問い合わせ項目<span class="required"><small>必須</small></span></p>
                    <input type="radio" class="radio" name="item" value="仕事" checked>商品について<span class="void_radio"></span>
                    <input type="radio" class="radio" name="item" value="採用">購入について<span class="void_radio"></span>
                    <input type="radio" class="radio" name="item" value="その他">その他
                    
                    <p>お名前<span class="required"><small>必須</small></span></p>
                    <input type="text" class="txt" name="name">
                    
                    <p>会社名</p>
                    <input type="text" class="txt" name="read">
                    
                    <p>郵便番号</p>
                    <input type="text" class="txt" name="read">

                    <p>都道府県</p>
                    <input type="text" class="txt" name="read">

                    <p>市区町村</p>
                    <input type="text" class="txt" name="read">

                    <p>番地・建物名</p>
                    <input type="text" class="txt" name="read">

                    <p>電話番号</p>
                    <input type="text" class="txt" name="tel">
                    
                    <p>メールアドレス<span class="required"><small>必須</small></span></p>
                    <input type="text" class="txt" name="mail">
                    
                    <p>お問い合わせ内容<span class="required"><small>必須</small></span></p>
                    <p><textarea name="matter" class="txtarea" cols="28" rows="16"></textarea></p>

                    <p>入力内容を再度ご確認のうえ、チェックを入れてください。<span class="required"><small>必須</small></span></p>
                    <p><input type="checkbox" name="news">内容を確認しました。</p>

                    <p><input type="submit" class="btnSubmit" value="送 信"></p>
                </form>
            </div>                               
        </div>

<%@include file="../footer.jsp" %>