<%@page import="java.sql.Connection"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList" %>
<%@page import="kr.seoil.vo.BoardVO" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<%
request.setCharacterEncoding("UTF-8");
String bCategory = (String)request.getAttribute("bCategory");
%>
<c:set var="bCategory" value="<%=bCategory%>" />
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>광고 관리</title>
	<link rel="stylesheet" type="text/css" href="../css/mypage.css?fs">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
	<script>
		function askAdvertiseDelete(){
			if(confirm("광고를 삭제하시겠습니까? 삭제된 광고는 복구가 불가능합니다.")){
				return true;
			}
			return false;
		}
		
		function askAdvertiseRevise(){
			if(confirm("광고를 수정하시겠습니까? 수정 후 재등록 해주시기 바랍니다.")){
				return true;
			}
			return false;
		}
		
		function askAdvertiseAccess(){
			if(confirm("광고를 등록하시겠습니까?")){
				return true;
			}
			return false;
		}
		
	</script>
</head>
<body>
<%@include file="../admin/page_header.jsp" %>
	<div class="page-container">
		<h2>광고 관리</h2>
		<div class="inner-content">
			<div class="search">
				<form class="d-flex" method="get" name="advertiseSearch" id="advertiseSearch" action="/advertiseManage.do?page=1&searchText=${searchText}">
					<div class="input-group">
						<input class="form-control" name="searchText" id=searchText value="${param.searchText}" type="text" maxlength=255 placeholder="검색어를 입력하세요">
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
						<p class="no-result">조회된 광고가 없습니다.</p>
					</c:when>
					<c:otherwise>
						<table class="search-table">
							<tr class="search-title">
								<th>번호</th><th>사진</th><th>광고 제목</th><th>작성일</th><th>수정일</th><th>등록일</th><th>보기</th><th>상태</th><th>등록</th><th>수정</th><th>삭제</th>
							</tr>
							<c:forEach var="advertise" items="${articles.messageList}">
								<tbody>
									<tr class="inner-tables">
										<td>${advertise.bId}</td>
										<td>
											<c:if test="${not empty advertise.image_path}" var="imageShow">
												<img src="${advertise.image_path}" style="width: 150px; height:100px;">
											</c:if>
										</td>
										
										<!-- 제목 미리보기 -->
										<td class="table-title">
											${advertise.bTitle}
										</td>
										<td>
											<fmt:formatDate pattern="yyyy-MM-dd" value="${advertise.bReg_date}"/>
										</td>
										<td>
											<fmt:formatDate pattern="yyyy-MM-dd" value="${advertise.bMod_date}"/>
										</td>
										<td>
											<fmt:formatDate pattern="yyyy-MM-dd" value="${advertise.bAcc_date}"/>
										</td>
										<td>
											<c:choose>
												<c:when test="${advertise.bAvailable eq 1}">
													<a href="boardContent.do?category=${advertise.bCategory}&idxno=${advertise.idxno}">보기</a>
												</c:when>
												<c:otherwise>
													<a href="boardPreview.do?id=${advertise.bStdID}&idxno=${advertise.idxno}">보기</a>
												</c:otherwise>
											</c:choose>
										</td>
										<td>
											<c:choose>
												<c:when test="${advertise.bAvailable eq 1}">
													<p>등록</p>
												</c:when>
												<c:otherwise>
													<p>미등록</p>
												</c:otherwise>
											</c:choose>
										</td>
										<td class="table-button">
											<c:choose>
												<c:when test="${advertise.bAvailable eq 1}">
													<input type="hidden" value="이미 등록된 광고입니다.">
												</c:when>
												<c:otherwise>
													<form class="btn-form" id="advertiseAccessForm" name="advertiseAccessForm" method="post" action="/advertiseManage.do" onsubmit="return askAdvertiseAccess()">
														<input type="hidden" name="btnValue" value="access">
														<input type="hidden" name="bId" value="${advertise.bId}">
														<input type="hidden" name="bReg_date" value="${advertise.bReg_date}">
														<input type="submit" value="등록">
													</form>
												</c:otherwise>
											</c:choose>
										</td>
										<td class="table-button">
											<form id="advertiseReviseForm" name="advertiseReviseForm" method="post" action="/advertiseupdateupload.do" onsubmit="return askAdvertiseRevise()"> 
												<input type="hidden" name="btnValue" value="revise">
												<input type="hidden" name="bId" value="${advertise.bId}">
												<input type="hidden" name="bReg_date" value="${advertise.bReg_date}">
												<input type="hidden" name=bAvailable value="${advertise.bAvailable}">
												<input type="submit" value="수정">
											</form>
										</td>
										<td class="table-button">
											<form id="advertiseDeleteForm" name="advertiseDeleteForm" method="post" action="/advertiseManage.do" onsubmit="return askAdvertiseDelete()">
												<input type="hidden" name="btnValue" value="delete">
												<input type="hidden" name="bId" value="${advertise.bId}">
												<input type="hidden" name="bReg_date" value="${advertise.bReg_date}">
												<input type="hidden" name="bAvailable" value="${advertise.bAvailable}">
												<input type="submit" value="삭제">
											</form>
										</td>
									</tr>
								</tbody>
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
	                    <a class="page-link" href="advertiseManage.do?page=${articles.startPage-1}&searchText=${searchText}">이전</a>
	                </c:if>
				</li>
				
				<c:forEach var="pageNum" begin="${articles.startPage}" end="${articles.endPage}" >
				    <li class="page-item">
				    	<a class="page-link" href="advertiseManage.do?page=${pageNum}&searchText=${searchText}">${pageNum}</a>
				    </li>
				</c:forEach>
			   
			    <li class="page-item">
			    	<c:if test="${articles.next}">
			      		<a class="page-link" href="advertiseManage.do?page=${articles.endPage+1}&searchText=${searchText}">다음</a>
			    	</c:if>
			    </li>
			</ul>
		<!-- 페이징 처리 끝 -->
		</div>
	</div>
</body>
</html>