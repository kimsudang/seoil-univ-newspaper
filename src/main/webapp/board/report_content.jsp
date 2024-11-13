<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<% 
request.setCharacterEncoding("utf-8"); 

%>
<!DOCTYPE HTML>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	
	<title>서일대학교 학보사</title>
	<link rel="stylesheet" type="text/css" href="../css/index.css?s">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://kit.fontawesome.com/ec08099fdd.js" crossorigin="anonymous"></script>
	<script src="//cdn.ckeditor.com/4.19.0/full/ckeditor.js"></script>
</head>
<body>
<div class="user-wrap">
		<div class="body-content">
			<div class="wrap-section">
			<section>
				<header class="article-content-header">
					<h3 class="news-title">${boardContent.getrTitle()}</h3>
					<div class="info-group">
						<article class="article-item">
							<ul class="article-information">
								<li>
									<i class="fa-solid fa-user">
										<span class="show-reporter">제보자명 ${boardContent.getrName()}</span>
									</i>			
								</li>
								<li>
									<i class="fa-solid fa-envelope">
										<span class="show-email">이메일 ${boardContent.getrEmail()}</span>
									</i>			
								</li>
								<li>
									<i class="fa-solid fa-clock">
										<span class="show-regdate">작성일 <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${boardContent.getrReg_date()}"/></span>
									</i>			
								</li>
							</ul>
						</article>
					</div>
				</header>

				<div class="inner-content">
					<div class="wrap-news">	
						<div class="wrap-news-article">
							<section id="articleViewContent" class="article-view-content">
								<article class="article-body">
									<div class="article-content-body">
										<article id="article-view-content-body">
											${boardContent.getrContent()}
										</article>
									</div>
								</article>
							</section>

						</div>		
					</div>
				</div>
			</section>
			</div>
		</div>
	</div>
	<%@ include file="footer.jsp"%>
</div>
</body>
</html>