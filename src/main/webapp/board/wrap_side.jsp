<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" type="text/css" href="../css/wrap.css?ds">
</head>
<body>
	<div class="wrap_side">
		<div class="wrap-sticky">
			<div class="recent-area">
				<article class="recent">
					<div class="navbar">
						<p>최신기사</p>
					</div>
					<section class="content">
					<c:forEach var="recentArticles" items="${recentArticles}" varStatus="status">
					<div class="items">
						<a class="wrap-recent" href="boardContent.do?category=${recentArticles.bCategory}&idxno=${recentArticles.idxno}">
							<em>${status.count}</em><span>${recentArticles.bTitle}</span>
						</a>
					</div>
					</c:forEach>
					</section>
				</article>
			</div>
			<div class="banner-area" id="banner-area">
				<article class="banner" id="banner">
					<a href="pdfViewList.do" >
						<img src="../img/pdf_banner.png" alt="pdf_download" class="banner-img">
					</a>
				</article>
				<article class="banner" id="banner">
					<a href="http://hm.seoil.ac.kr/" >
						<img src="../img/site_banner.png" alt="site_move" class="banner-img">
					</a>
				</article>
			</div>
			
			<div class="opinion-area" id="opinion-area">
				<article class="opinion">
					<div class="navbar">
						<p>오피니언</p>
						<a href="news.do?page=1&category=2"><i class="fa-solid fa-plus"></i></a>
					</div>
					<c:forEach var="studentColumnArticle" items="${studentColumnArticle}">
					<div class="wrap-sub">			
						<div class="pic">
							<c:if test="${not empty studentColumnArticle.image_path }" var="imageShow">
								<td>
									<div>
										<a href="boardContent.do?category=${studentColumnArticle.bCategory}&idxno=${studentColumnArticle.idxno}">
										<img src="${studentColumnArticle.image_path}" style="width: 120px; height:100px;"></a>
									</div>
								</td>
							</c:if>
						</div>
						<div class="wrap-newstext">
							<h4>
								<a href="boardContent.do?category=${studentColumnArticle.bCategory}&idxno=${studentColumnArticle.idxno}">
									${studentColumnArticle.bTitle}
								</a>
							</h4>
						</div>							
					</div>
					</c:forEach>
					<c:forEach var="professorColumnArticle" items="${professorColumnArticle}">
					<div class="wrap-sub">			
						<div class="pic">
							<c:if test="${not empty professorColumnArticle.image_path }" var="imageShow">
								<td>
									<div>
										<a href="boardContent.do?category=${professorColumnArticle.bCategory}&idxno=${professorColumnArticle.idxno}">
										<img src="${professorColumnArticle.image_path}" style="width: 120px; height:100px;"></a>
									</div>
								</td>
							</c:if>
						</div>
						<div class="wrap-newstext">
							<h4>
								<a href="boardContent.do?category=${professorColumnArticle.bCategory}&idxno=${professorColumnArticle.idxno}">
									${professorColumnArticle.bTitle}
								</a>
							</h4>
						</div>							
					</div>
					</c:forEach>
					<c:forEach var="editorialArticle" items="${editorialArticle}">
					<div class="wrap-sub" style="height:60px;">			
						<div class="pic">
							<c:if test="${not empty editorialArticle.image_path }" var="imageShow">
								<td>
									<div>
										<a href="boardContent.do?category=${editorialArticle.bCategory}&idxno=${editorialArticle.idxno}">
										<img src="${editorialArticle.image_path}"></a>
									</div>
								</td>
							</c:if>
						</div>
						<div class="edit-newstext">
							<h4>
								<a href="boardContent.do?category=${editorialArticle.bCategory}&idxno=${editorialArticle.idxno}">
									${editorialArticle.bTitle}
								</a>
							</h4>
						</div>							
					</div>
					</c:forEach>
				</article>
			</div>
			<div class="banner-area" id="banner-area">
				<article class="banner" id="banner">
					<a href="/news.do?page=1&category=16" >
						<img src="../img/article.png" alt="article" class="banner-img">
					</a>
				</article>
				<article class="banner" id="banner">
					<a href="https://www.instagram.com/seoil_press/?__coig_restricted=1" >
						<img src="../img/instagram_banner.png" alt="instagram" class="banner-img">
					</a>
				</article>
			</div>
		</div>
	</div>
</body>
</html>