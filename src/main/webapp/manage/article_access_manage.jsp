<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%
request.setCharacterEncoding("UTF-8");
%>

<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	
	<title>미승인 기사관리</title>
	<link rel="stylesheet" type="text/css" href="../css/mypage.css?ss">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
	
	<script>
		function askArticleAccess(){
			if(confirm("기사를 승인하시겠습니까?")){
				return true;
			}
			return false;
		}
		
		function askArticleDefer(){
			if(confirm("기사를 반려하시겠습니까?")){
				return true;
			}
			return false;
		}
	</script>
</head>
<body>
<%@include file="../admin/page_header.jsp" %>

	<div class="page-container">
		<h2>미승인 기사 목록</h2>
		<div class="inner-content">
			<div class="search">
				<form class="d-flex" method="get" name="adminSearch" id="adminSearch" action="/articleAccessManage.do?page=1&searchType=${articles.searchType}&searchText=${articles.searchText}">
					<select name="searchType" class="form-select form-select-sm" onchange="">
						<option value="allType" <c:if test="${ articles.searchType == 'allType'}">selected</c:if>>전체</option>
						<option value="name" <c:if test="${ articles.searchType == 'name'}">selected</c:if>>기자명</option>
						<option value="title" <c:if test="${ articles.searchType == 'title'}">selected</c:if>>기사제목</option>
						<option value="subTitle" <c:if test="${ articles.searchType == 'subTitle'}">selected</c:if>>서브제목</option>
						<option value="content" <c:if test="${ articles.searchType == 'content'}">selected</c:if>>기사내용</option>
					</select>
					<div class="input-group">
						<input class="form-control" name="searchText" id="searchText" value="${articles.searchText}" type="text" maxlength=255 placeholder="검색어를 입력하세요">
							<div class="input-group-append">
								<button class="search-button" type="submit" onclick="textBold()">
									<i class="fa-solid fa-magnifying-glass"></i>
								</button>
							</div>
					</div>	
				</form>
			</div>
			<div class="panel-body">
				<c:choose>
					<c:when test="${empty articles.messageList}">
						<p class="no-result">조회된 기사가 없습니다.</p>
					</c:when>
					<c:otherwise>
						<table class="search-table" border="1">
							<tr class="search-title"><th>번호</th><th>학번</th><th>이름</th><th>사진</th><th>기사 제목</th><th>카테고리</th><th>작성일</th><th>수정일</th><th>보기</th><th>상태</th><th>승인</th><th>반려</th></tr>
							<c:forEach var="article" items="${articles.messageList}">
								<tr class="inner-tables">
									<td>${article.bId}</td>
									<td>${article.bStdID}</td>
									<td>${article.userName}</td>
									<td>
										<c:if test="${not empty article.image_path}" var="imageShow">
											<img src="${article.image_path}" style="width: 150px; height:100px;">
										</c:if>
									</td>
									<td class="table-title">
										${article.bTitle}
									</td>
									<td class="table-category">
										<c:set var="categoryState" value="${fn:split(article.cpath, '|')}"></c:set>
										<ul>
											<c:forEach var="categoryValue" items="${categoryState}">
												<li class="category">${categoryValue}</li>
											</c:forEach>
										</ul>
									</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd" value="${article.bReg_date}"/></td>
									<td><fmt:formatDate pattern="yyyy-MM-dd" value="${article.bMod_date}"/></td>
									<td>
										<c:choose>
											<c:when test="${article.bAvailable == 1}">
												<a href="boardContent.do?category=${article.bCategory}&idxno=${article.idxno}">보기</a>
											</c:when>
											<c:otherwise>
												<a href="boardPreview.do?id=${article.bStdID}&idxno=${article.idxno}">보기</a>
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${article.bAvailable == 1}"><p>승인</p></c:when>
											<c:when test="${article.bAvailable == 3}"><p>반려</p></c:when>
											 <c:otherwise><p>미승인</p></c:otherwise>
										</c:choose>
									</td>
									<td class="table-button">
										<c:choose>
											<c:when test="${article.bAvailable != 1}">
												<form id="articleAccessForm" name="articleAccessForm" method="post" action="/articleAccessManage.do" onsubmit="return askArticleAccess()">
													<input type="hidden" name="btnValue" value="access">
													<input type="hidden" name="bId" value="${article.bId}">
													<input type="hidden" name="bReg_date" value="${article.bReg_date}">
													<input type="submit" value="승인">
												</form>
											</c:when>
											<c:otherwise>
												<input type="hidden">
											</c:otherwise>
										</c:choose>	
									</td>
									<td class="table-button">
										<c:choose>
											<c:when test="${article.bAvailable != 1 && article.bAvailable !=3}">
												<form id="articleDeferForm" name="articleDeferForm" method="post" action="/articleAccessManage.do" onsubmit="return askArticleDefer()">
													<input type="hidden" name="btnValue" value="defer">
													<input type="hidden" name="bId" value="${article.bId}">
													<input type="hidden" name="bReg_date" value="${article.bReg_date}">
													<input type="submit" value="반려">
												</form>
											</c:when>
											<c:otherwise>
												<input type="hidden">
											</c:otherwise>
										</c:choose>	
									</td>
								</tr>
							</c:forEach>
						</table>
					</c:otherwise>
				</c:choose>
				
			</div>
		</div>
		<div class="page-area">
			<!-- 페이징 처리 시작 -->
			<c:choose>
				<c:when test="${articles.searchText eq null}">
					<ul class="pagination justify-content-center">
						<li class="page-item">
							<c:if test="${articles.prev}">
			                    <a class="page-link" href="articleAccessManage.do?page=${articles.startPage-1}">이전</a>
			                </c:if>
						</li>
						
						<c:forEach var="pageNum" begin="${articles.startPage}" end="${articles.endPage}" >
						    <li class="page-item">
						    	<a class="page-link" href="articleAccessManage.do?page=${pageNum}">${pageNum}</a>
						    </li>
						</c:forEach>
					   
					    <li class="page-item">
					    	<c:if test="${articles.next}">
					      		<a class="page-link" href="articleAccessManage.do?page=${articles.endPage+1}">다음</a>
					    	</c:if>
					    </li>
					</ul>		
			    </c:when>
			    <c:otherwise>
			    	<ul class="pagination justify-content-center">
						<li class="page-item">
							<c:if test="${articles.prev}">
			                    <a class="page-link" href="articleAccessManage.do?page=${articles.startPage-1}&searchType=${articles.searchType}&searchText=${articles.searchText}">이전</a>
			                </c:if>
						</li>
						
						<c:forEach var="pageNum" begin="${articles.startPage}" end="${articles.endPage}" >
						    <li class="page-item">
						    	<a class="page-link" href="articleAccessManage.do?page=${pageNum}&searchType=${articles.searchType}&searchText=${articles.searchText}">${pageNum}</a>
						    </li>
						</c:forEach>
					   
					    <li class="page-item">
					    	<c:if test="${articles.next}">
					      		<a class="page-link" href="articleAccessManage.do?page=${articles.endPage+1}&searchType=${articles.searchType}&searchText=${articles.searchText}" >다음</a>
					    	</c:if>
					    </li>
					</ul>
			    </c:otherwise>
		    </c:choose>
			<!-- 페이징 처리 끝 -->
		</div>
	</div>
</body>
</html>