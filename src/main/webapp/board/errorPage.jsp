<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<style>
		body{
			background-color : #ecf0f1;
		}
		
		body, th, td, input, select, textarea, button, h1, h2, h3, h4, h5, h6 {
		    font-family: 'Helvetica Neue','Roboto','Noto Sans KR','Malgun Gothic','dotum','Arial',sans-serif;
		}
		
		.error-page {
		    display: -webkit-box;
		    display: -webkit-flex;
		    display: -ms-flexbox;
		    display: flex;
		    -ms-justify-content: center;
		    -webkit-justify-content: center;
		    justify-content: center;
		    -ms-align-items: center;
		    -webkit-align-items: center;
		    align-items: center;
		    height: 100vh;
		}
		
		.outer{
		    display: block;
		    max-width: 475px;
		    padding: 1.25rem;
		    text-align: center;
		}
		
		header {
		    margin-bottom: 1.875rem;
		}
		
		header>.image-yongyong{
			text-align: center;
			top:50%; left:50%;
		    overflow: hidden;
		}
		
		header > .image-yongyong > .yongyong{
			max-width : 200px;
			max-height: 200px;
		    object-fit: contain;
		}
		
		header>.error-code {
		    display: block;
		    font-size: 2.25rem;
		    line-height: 1.25;
		    letter-spacing: 0em;
		}
		
		section>.inner-detail {
		    line-height: 1.4em;
		    display: block;
		    margin-bottom: 10px;
		    text-align: center;
		    color: #999999;
		}
		
		.fa-house:before{
			content:"\f015";
			display: inline-block;
		    font-family: FontAwesome;
		    font-size: inherit;
			line-height: 40px;    
		    text-rendering: auto;
		    -webkit-font-smoothing: antialiased;
		}
		
		section >.btn-section{
			margin-top : 20px;
		}
		
		section > .btn-section > .button {
		    margin: 0;
		    margin-right: 1px;
		    margin-bottom: 1px;
		    padding-left: 20px;
		    padding-right: 20px;
		    font-size: 1rem;
		    color: #686868!important;
		    background-color: #fff;
		    border-color: #ced2db;
		
		    border-radius: 3px;
		}
		
		section > .btn-section > .button > .show-alt{
			position: absolute!important;
		    width: 1px;
		    height: 1px;
		    overflow: hidden;
		    clip: rect(0,0,0,0);
		}
	</style>
	<meta charset="UTF-8">
	<script src="https://kit.fontawesome.com/f1def33959.js" crossorigin="anonymous"></script>
	<title>Error</title>
</head>
<body>
	<div class="error-page">
	    <div class="outer">
	    	<header>
		    	<div class="image-yongyong"><img class="yongyong" src="../img/emo-no-background.gif"></div>
		    	<h1 class="error-code">요청하신 페이지를</br>찾을 수 없습니다.</h1>
	    	</header>
	    	<section>
	    		<span class="inner-detail">서비스 이용에 불편을 드려 죄송합니다.<br>
				아래 버튼을 눌러주시면 학보사 메인창으로 이동합니다.</span>
				<div class="btn-section">
					<button type="button" class="button" onclick="location.href='/home.do'">
					<i class="fa-solid fa-house"></i><span class="show-alt">홈으로</span></button>
				</div>		
	    	</section>
	        
	    </div>
	</div>
</body>
</html>