<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<% 
request.setCharacterEncoding("utf-8");
String bCategory = (String)request.getAttribute("bCategory");
%>
<c:set var="bCategory" value="<%=bCategory%>"/>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />

<title>서일대학교 학보사</title>
<link rel="stylesheet" type="text/css" href="../css/index.css?dks">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://kit.fontawesome.com/ec08099fdd.js" crossorigin="anonymous"></script>

</head>
<body>
<div class="user-wrap">
	<div class="wrap">
		<div class="body-content">
			<div class="wrap-section">
				<div class="inner-content">
					<div class="list-inner-content">
						<div class="satirical-area">
							<div class="only-satirical">
								<c:forEach var="article" items="${articles.messageList}">
								<div class="each-satirical">
									<div class="each-area">
										<a href="boardContent.do?category=${article.bCategory}&idxno=${article.idxno}">
											<img src="${article.image_path}" style="width: 100%; height:100%; object-fit: contain;">
										</a>
									</div>
									<div class="article-title">
										<a href="boardContent.do?category=${article.bCategory}&idxno=${article.idxno}">
											${article.bTitle}
										</a>
									</div>
								</div>
								</c:forEach>
							</div>
						</div>
					</div>
					<%@ include file="wrap_side.jsp" %>
				</div>
			</div>		
			<div class="empty"></div>
			<!-- 페이징 처리 시작 -->
			<ul class="pagination justify-content-center">
				<li class="page-item">
					<c:if test="${articles.prev}">
	                    <a class="page-link" href="news.do?page=${articles.startPage-1}&category=${bCategory}">이전</a>
	                </c:if>
				</li>
				
				<c:forEach var="pageNum" begin="${articles.startPage}" end="${articles.endPage}" >
				    <li class="page-item">
				    	<a class="page-link" href="news.do?page=${pageNum}&category=${bCategory}">${pageNum}</a>
				    </li>
				</c:forEach>
			   
			    <li class="page-item">
			    	<c:if test="${articles.next}">
			      		<a class="page-link" href="news.do?page=${articles.endPage+1}&category=${bCategory}">다음</a>
			    	</c:if>
			    </li>
			</ul>
		</div>
	</div>
	<%@ include file="footer.jsp" %>
	</div>
</body>
</html>