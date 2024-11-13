<%@ page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	<title>등록 기사</title>
	<link rel="stylesheet" type="text/css" href="../css/mypage.css?sir">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
	
	<script type="text/javascript">
		function askArticleRevise(){
			if(confirm("기사를 수정 가능 상태로 변경하시겠습니까? 기사는 수정 후 승인 전까지 일반 사용자에게 보이지 않습니다.")){
				return true;
			}
			return false;
		}
		
		function askArticleMain(){
			if(confirm("해당 기사를 대표 기사로 설정하시겠습니까?")){
				return true;
			}
			return false;
		}
		
		function askArticleAnnounce(){
			if(confirm("해당 기사를 학생의 목소리의 공지로 설정하시겠습니까?")){
				return true;
			}
			return false;
		}
		
		function askArticleDelete(){
			if(confirm("기사를 삭제하시겠습니까? 삭제된 기사는 복구할 수 없습니다. 한번 더 확인해주세요.")){
				return true;
			}
			return false;
		}
	</script>
	
</head>
<body>
<%@ include file="../admin/page_header.jsp" %>
	<div class="page-container">
		<h2>등록 기사 목록</h2>
		<div class="inner-content">
			<div class="search">
				<form class="d-flex" method="get" name="adminSearch" id="adminSearch" action="/articleManage.do?page=1&searchType=${articles.searchType}&searchText=${articles.searchText}">
					<select name="searchType" class="form-select form-select-sm" onchange="">
						<option value="allType" <c:if test="${ articles.searchType == 'allType'}">selected</c:if>>전체</option>
						<option value="name" <c:if test="${ articles.searchType == 'name'}">selected</c:if>>기자명</option>
						<option value="title" <c:if test="${ articles.searchType == 'title'}">selected</c:if>>기사제목</option>
						<option value="subTitle" <c:if test="${ articles.searchType == 'subTitle'}">selected</c:if>>서브제목</option>
						<option value="content" <c:if test="${ articles.searchType == 'content'}">selected</c:if>>기사내용</option>
					</select>
					<div class="input-group">
						<input class="form-control" name="searchText" id="searchText" value="${param.searchText}" type="text" maxlength=255 placeholder="검색어를 입력하세요">
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
							<tr class="search-title"><th>대표기사</th><th>공지</th><th>번호</th><th>학번</th><th>이름</th><th>사진</th><th>기사 제목</th><th>카테고리</th><th>작성일</th><th>수정일</th><th>등록일</th><th>보기</th><th>수정가능</th><th>삭제</th></tr>
							<c:forEach var="article" items="${articles.messageList}">
								<tr class="inner-tables">
									<td class="table-button">
									<c:choose>
										<c:when test="${article.bMain == 0 && article.bCategory != '14' && article.bCategory != '17'}">
										<form id="articleMainForm" name="articleMainForm" method="post" action="/articleManage.do" onsubmit="return askArticleMain()">
											<input type="hidden" name="btnValue" value="setMain">
											<input type="hidden" name="bId" value="${article.bId}">
											<input type="hidden" name="bReg_date" value="${article.bReg_date}">
											<input type="submit" value="등록">
										</form>
										</c:when>
										<c:otherwise>
											<input type="hidden" value="안보임">
										</c:otherwise>
									</c:choose>
									</td>
									<td class="table-button">
									<c:choose>
										<c:when test="${article.bAnnounce == 0 && article.bCategory == '17'}">
										<form id="articleAnnounceForm" name="articleAnnounceForm" method="post" action="/articleManage.do" onsubmit="return askArticleAnnounce()">
											<input type="hidden" name="btnValue" value="setAnnounce">
											<input type="hidden" name="bId" value="${article.bId}">
											<input type="hidden" name="bReg_date" value="${article.bReg_date}">
											<input type="submit" value="공지">
										</form>
										</c:when>
										<c:otherwise>
											<input type="hidden" value="안보임">
										</c:otherwise>
									</c:choose>
									</td>
									<td>${article.bId}</td>
									<td>${article.bStdID}</td>
									<td>${article.userName}</td>
									<td>
										<c:if test="${not empty article.image_path}" var="imageShow">
											<img src="${article.image_path}" style="width: 100%; max-width: 150px; max-height:100px;">
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
										<a href="boardContent.do?category=${article.bCategory}&idxno=${article.idxno}">보기</a>
									</td>
									<td class="table-button">
										<c:choose>
											<c:when test="${article.bMain == 0 && article.bAnnounce == 0}">
												<form id="articleReviseForm" name="articleReviseForm" method="post" action="/articleManage.do" onsubmit="return askArticleRevise()">
													<input type="hidden" name="btnValue" value="revise">
													<input type="hidden" name="bId" value="${article.bId}">
													<input type="hidden" name="bReg_date" value="${article.bReg_date}">
													<input type="hidden" name="bMain" value="${article.bMain}">
													<input type="submit" value="변경">
												</form>
											</c:when>
											<c:otherwise>
												<input type="hidden" value="안보임">
											</c:otherwise>
										</c:choose>
									</td>
									<td class="table-button">
										<c:choose>
											<c:when test="${article.bMain == 0 && article.bAnnounce == 0}">
												<form id="articleDeleteForm" name="articleDeleteForm" method="post" action="/articleManage.do" onsubmit="return askArticleDelete()">
													<input type="hidden" name="btnValue" value="delete">
													<input type="hidden" name="bId" value="${article.bId}">
													<input type="hidden" name="bReg_date" value="${article.bReg_date}">
													<input type="hidden" name="bMain" value="${article.bMain}">
													<input type="submit" value="삭제">
												</form>
											</c:when>
											<c:otherwise>
												<input type="hidden" value="안보임">
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
			                    <a class="page-link" href="articleManage.do?page=${articles.startPage-1}" style="background-color: #ff52a0; margin-top: 0; height: 40px; color: white; border: 0px solid #f78f24;">이전</a>
			                </c:if>
						</li>
						
						<c:forEach var="pageNum" begin="${articles.startPage}" end="${articles.endPage}" >
						    <li class="page-item">
						    	<a class="page-link" style="margin-top: 0; height: 40px; color: pink; border: 1px solid pink;"
						    	href="articleManage.do?page=${pageNum}">${pageNum}</a>
						    </li>
						</c:forEach>
					   
					    <li class="page-item">
					    	<c:if test="${articles.next}">
					      		<a class="page-link" href="articleManage.do?page=${articles.endPage+1}" style="background-color: #ff52a0; margin-top: 0; height: 40px; color: white; border: 0px solid #f78f24;">다음</a>
					    	</c:if>
					    </li>
					</ul>
			    </c:when>
			    <c:otherwise>
			    	<ul class="pagination justify-content-center">
						<li class="page-item">
							<c:if test="${articles.prev}">
			                    <a class="page-link" href="articleManage.do?page=${articles.startPage-1}&searchType=${articles.searchType}&searchText=${articles.searchText}">이전</a>
			                </c:if>
						</li>
						
						<c:forEach var="pageNum" begin="${articles.startPage}" end="${articles.endPage}" >
						    <li class="page-item">
						    	<a class="page-link" href="articleManage.do?page=${pageNum}&searchType=${articles.searchType}&searchText=${articles.searchText}">${pageNum}</a>
						    </li>
						</c:forEach>
					   
					    <li class="page-item">
					    	<c:if test="${articles.next}">
					      		<a class="page-link" href="articleManage.do?page=${articles.endPage+1}&searchType=${articles.searchType}&searchText=${articles.searchText}">다음</a>
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