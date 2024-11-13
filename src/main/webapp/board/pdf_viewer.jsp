<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>pdf</title>
</head>
<body style="height: 100%; width: 100%; overflow: hidden; margin:0px; background-color: rgb(82, 86, 89);">
	<object type="application/pdf" data="${articles}" style="position:absolute; left: 0; top: 0; color: white;" width="100%" height="100%">
		<div class="pdf-error" style="padding: 10% 40% 10% 40%">
			<p style="font-size: 1.2rem; font-weight: bold; padding-bottom: 2%">
			pdf 파일을 불러오는데 문제가 생겼습니다.<br>
			관리자에게 문의 바랍니다.
			</p>
			<div class="banner-area">
				<article class="banner">
					<a href="pdfViewList.do" >
						<img src="../img/pdf_banner.png" alt="pdf_download" class="banner-img" width="400px" height="160px">
					</a>
				</article>
				<article class="banner">
					<a href="http://hm.seoil.ac.kr/?kr" >
						<img src="../img/site_banner.png" alt="site_move" class="banner-img"  width="400px" height="160px">
					</a>
				</article>
			</div>
		</div>
	</object>
</body>
</html>