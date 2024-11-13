<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="header.jsp"%>

<%
request.setCharacterEncoding("UTF-8");
%>

<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	
	<title>검색 결과</title>
	<link rel="stylesheet" type="text/css" href="../css/index.css?ff">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://kit.fontawesome.com/ec08099fdd.js" crossorigin="anonymous"></script>
</head>
<body>
<div class="user-wrap">
	<div class="wrap">
		<div class="body-content">
			<div class="wrap-section">
				<div class="inner-content">
					<c:choose>
						<c:when test="${empty articles.messageList}">
							<p class="no-result">검색 결과가 없습니다.</p>
						</c:when>
						<c:otherwise>
							<div class="list-inner-content">
							<c:forEach var="article" items="${articles.messageList}">
								<div class="merge-list">
									<div class="list">
										<c:if test="${not empty article.image_path}" var="imageShow">
											<div class="list-pic">
												<a href="boardContent.do?category=${article.bCategory}&idxno=${article.idxno}">
												<img src="${article.image_path}" style="width: 150px; height:100px;"></a>
											</div>
										</c:if>
										
										<!-- 제목 미리보기 -->
										<div class="list-content">
											<div class="article-title">
												<a href="boardContent.do?category=${article.bCategory}&idxno=${article.idxno}">
													<c:choose>
														<c:when test="${fn:length(article.bTitle) > 26}">
															${fn:substring(article.bTitle, 0, 25)}...
														</c:when>
														<c:otherwise>
															${article.bTitle}
														</c:otherwise>
													</c:choose>
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
							</div>
						</c:otherwise>
					</c:choose>
					<%@ include file="wrap_side.jsp" %>
					</div>
				</div>
			</div>		
			<!-- 페이징 처리 시작 -->
			<c:choose>
				<c:when test="${article.searchText.equals('')}">
					<ul class="pagination justify-content-center">
						<li class="page-item">
							<c:if test="${articles.prev}">
			                    <a class="page-link" href="mainSearch.do?page=${articles.startPage-1}" style="background-color: #ff52a0; margin-top: 0; height: 40px; color: white; border: 0px solid #f78f24;">이전</a>
			                </c:if>
						</li>
						
						<c:forEach var="pageNum" begin="${articles.startPage}" end="${articles.endPage}" >
						    <li class="page-item">
						    	<a class="page-link" style="margin-top: 0; height: 40px; color: pink; border: 1px solid pink;"
						    	href="mainSearch.do?page=${pageNum}">${pageNum}</a>
						    </li>
						</c:forEach>
					   
					    <li class="page-item">
					    	<c:if test="${articles.next}">
					      		<a class="page-link" href="mainSearch.do?page=${articles.endPage + 1 }" style="background-color: #ff52a0; margin-top: 0; height: 40px; color: white; border: 0px solid #f78f24;">다음</a>
					    	</c:if>
					    </li>
					</ul>
					
				</c:when>
				<c:otherwise>
				<ul class="pagination justify-content-center">
					<li class="page-item">
						<c:if test="${articles.prev}">
		                    <a class="page-link" href="mainSearch.do?page=${articles.startPage-1}&searchText=${articles.searchText}">이전</a>
		                </c:if>
					</li>
					
					<c:forEach var="pageNum" begin="${articles.startPage}" end="${articles.endPage}" >
					    <li class="page-item">
					    	<a class="page-link" href="mainSearch.do?page=${pageNum}&searchText=${articles.searchText}">${pageNum}</a>
					    </li>
					</c:forEach>
				   
				    <li class="page-item">
				    	<c:if test="${articles.next}">
				      		<a class="page-link" href="mainSearch.do?page=${articles.endPage+1}&searchText=${articles.searchText}">다음</a>
				    	</c:if>
				    </li>
				</ul>
				</c:otherwise>
			</c:choose>
			<!-- 페이징 처리 끝 -->
		</div>
	<%@ include file="footer.jsp" %>
	</div>
</body>
</html>