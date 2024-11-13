<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<% 
request.setCharacterEncoding("utf-8");
String bCategory = (String)request.getAttribute("bCategory");
String section = "1";
%>
<c:set var="bCategory" value="<%=bCategory%>"/>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />

<title>서일대학교 학보사</title>
<link rel="stylesheet" type="text/css" href="../css/index.css?f">
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
					<c:choose>
						<c:when test="${bCategory eq '13'}">
							<div class="satirical-area">
								<h2>최신 만평</h2>
								<c:forEach var="formChange" begin="0" end="0" step="1">
									<div class="now-area">
										<c:forEach var="article" items="${articles.messageList}" begin="0" end="3" step="1">
										<div class="merge-satirical">
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
									<div class="empty"></div>
									<div class="satirical-before">
										<h2>이전 만평</h2>
										<div class="satirical-inner">
											<a href="satirical.do?page=1&category=13&section=<%=section%>">
												<p>만평 더보기</p>
												<i class="fa-regular fa-square-plus plus-btn"></i>
											</a>
										</div>
									</div>
									<div class="now-area">
										<c:forEach var="article" items="${articles.messageList}" begin="4" end="7" step="1">
										<div class="merge-satirical">
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
								</c:forEach>
							</div>
						</c:when>
						<c:when test="${bCategory eq '17'}">
							<c:forEach var="article" items="${articles.messageList}">
							<div class="std-list">
								<div class="in-std-list">
								<!-- 제목 미리보기 -->
								<div class="std-list-content">
									<c:choose>
										<c:when test="${article.bAnnounce eq 1}">
											<div class="article-title">
												<a href="boardContent.do?category=${article.bCategory}&idxno=${article.idxno}">
													${article.bTitle}
												</a>
											</div>
											<!-- 등록일 미리보기 -->
											<div class="std-list-date">
												<a href="boardContent.do?category=${article.bCategory}&idxno=${article.idxno}">
													<fmt:formatDate pattern="yyyy-MM-dd" value="${article.bAcc_date}"/>
												</a>
											</div>
										</c:when>
										<c:otherwise>
											<div class="article-title font-l">
												<a href="boardContent.do?category=${article.bCategory}&idxno=${article.idxno}">
													${article.bTitle}
												</a>
											</div>
											<!-- 등록일 미리보기 -->
											<div class="std-list-date">
												<a href="boardContent.do?category=${article.bCategory}&idxno=${article.idxno}">
													<fmt:formatDate pattern="yyyy-MM-dd" value="${article.bAcc_date}"/>
												</a>
											</div>
										</c:otherwise>
									</c:choose>
								</div>
								<br>
								<hr>
								</div>
							</div>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<c:forEach var="article" items="${articles.messageList}">
							<div class="merge-list">
								<div class="list">
									<c:if test="${not empty article.image_path}" var="imageShow">
										<div class="list-pic">
											<a href="boardContent.do?category=${article.bCategory}&idxno=${article.idxno}">
											<img src="${article.image_path}"></a>
										</div>
									</c:if>
									
									<!-- 제목 미리보기 -->
									<div class="list-content">
										<div class="article-title">
											<a href="boardContent.do?category=${article.bCategory}&idxno=${article.idxno}">
												${article.bTitle}
											</a>
										</div>
										<!-- 게시글 미리보기 -->
										<div class="list-preview-content">
											<a href="boardContent.do?category=${article.bCategory}&idxno=${article.idxno}">
												${article.text_only}
											</a>
										</div>
										<!-- 등록일 미리보기 -->
										<div class="list-date">
											<a href="boardContent.do?category=${article.bCategory}&idxno=${article.idxno}">
												<fmt:formatDate pattern="yyyy-MM-dd" value="${article.bAcc_date}"/>
											</a>
										</div>
									</div>
									<br>
									<hr>
									</div>
								</div>
								</c:forEach>
							</c:otherwise>
						</c:choose>
						</div>
						<%@ include file="wrap_side.jsp" %>
					</div>
				</div>		
				<div class="empty"></div>
				<c:choose>
					<c:when test="${bCategory eq '13'}"></c:when>
					<c:otherwise>
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
					</c:otherwise>
				</c:choose>
		</div>
	</div>
	<%@ include file="footer.jsp" %>
	</div>
</body>
</html>