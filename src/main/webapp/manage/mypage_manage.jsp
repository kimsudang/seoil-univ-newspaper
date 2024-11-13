<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@page import="kr.seoil.vo.BoardVO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%
request.setCharacterEncoding("UTF-8");
%>
<%@ include file="../admin/page_header.jsp" %>
<!DOCTYPE html>
<html>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<link rel="stylesheet" type="text/css" href="../css/mypage.css?s">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
	<title>개인 기사 관리</title>
	<script type="text/javascript">
			function askMyArticleRevise(){
				if(confirm("기사를 수정하시겠습니까?")){
					return true;
				}
				return false;
			}
			
			function askMyArticlePreview(){
				if(confirm("기사를 보시겠습니까?")){
					return true;
				}
				return false;
			}
	</script>
</head>
<body>
	<div class="page-container">
		<h2>내 기사 목록</h2>
		<div class="inner-content">
			<div class="search">
				<form class="d-flex" method="post" name="myPageSearch" id="myPageSearch" action="/mypageManage.do?page=1&bStdID=${bStdID}">
					<select name="searchType" class="form-select form-select-sm" onchange="">
						<option value="allType" <c:if test="${ articles.searchType == 'allType'}">selected</c:if>>전체</option>
						<option value="title" <c:if test="${ articles.searchType == 'title'}">selected</c:if>>기사제목</option>
						<option value="content" <c:if test="${ articles.searchType == 'content'}">selected</c:if>>기사내용</option>
					</select>
					<div class="input-group">
						<input class="form-control" name="searchText" id="searchText" value="${articles.searchText}" type="text" maxlength=255 placeholder="검색어를 입력하세요">
							<div class="input-group-append">
								<button class="search-button" type="submit">
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
							<tr class="search-title"><th>번호</th><th>사진</th><th>기사 제목</th><th>카테고리</th><th>작성일</th><th>수정일</th><th>등록일</th><th>보기</th><th>상태</th><th>수정</th></tr>
							<c:forEach var="article" items="${articles.messageList}">
								<tr class="inner-tables">
									<td>${article.rownum}</td>
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
									<td><fmt:formatDate pattern="yyyy-MM-dd" value="${article.bAcc_date}"/></td>
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
											 <c:when test="${article.bAvailable == 0}"><p>미승인</p></c:when>
											 <c:when test="${article.bAvailable == 1}"><p>승인</p></c:when>
											 <c:when test="${article.bAvailable == 3}"><p>반려</p></c:when> 
										</c:choose>
									</td>
									<td class="table-button">
										<c:choose>
											<c:when test="${article.bAvailable == 1}">
												<input type="hidden" value="승인된 기사는 수정 불가합니다.">
											</c:when>
											<c:otherwise>
												<form id="myArtiReviseForm" name="myArtiReviseForm" method="post" action="/contentupdateupload.do" onsubmit="return askMyArticleRevise()">
													<input type="hidden" name="bStdID" value="${userSessionId}">
													<input type="hidden" name="btnValue" value="revise">
													<input type="hidden" name="bId" value="${article.bId}">
													<input type="hidden" name="bReg_date" value="${article.bReg_date}">
													<input type="submit" value="수정">
												</form>
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
			<ul class="pagination justify-content-center">
				<li class="page-item">
					<c:if test="${articles.prev}">
	                    <a class="page-link" href="mypageManage.do?page=${articles.startPage-1}&searchType=${searchType}&searchText=${searchText}">이전</a>
	                </c:if>
				</li>
				
				<c:forEach var="pageNum" begin="${articles.startPage}" end="${articles.endPage}" >
				    <li class="page-item">
				    	<a class="page-link" href="mypageManage.do?page=${pageNum}&searchType=${searchType}&searchText=${searchText}">${pageNum}</a>
				    </li>
				</c:forEach>
			   
			    <li class="page-item">
			    	<c:if test="${articles.next}">
			      		<a class="page-link" href="mypageManage.do?page=${articles.endPage+1}&searchType=${searchType}&searchText=${searchText}">다음</a>
			    	</c:if>
			    </li>
			</ul>
			<!-- 페이징 처리 끝 -->
		</div>
	</div>
</body>
</html>