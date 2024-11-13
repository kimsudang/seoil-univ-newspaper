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
	
	<title>기사 제보 관리</title>
	<link rel="stylesheet" type="text/css" href="../css/mypage.css?ssds">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
	
	<script>
		function askReportDelete(){
			if(confirm("기사를 삭제하시겠습니까?")){
				return true;
			}
			return false;
		}
	</script>
</head>
<body>
<%@include file="../admin/page_header.jsp" %>
	
	<div class="page-container">
		<h2>기사 제보 목록</h2>
		<div class="inner-content">
			<div class="panel-body">
				<c:choose>
					<c:when test="${empty articles.messageList}">
						<p class="no-result">제보된 기사가 없습니다.</p>
					</c:when>
					<c:otherwise>
						<table class="search-table" border="1">
							<tr class="search-title"><th>번호</th><th>학번</th><th>구분</th><th>소속</th><th>이름</th><th>기사 제목</th><th>이메일</th><th>연락처</th><th>작성일</th><th>보기</th><th>삭제</th></tr>
								<c:forEach var="article" items="${articles.messageList}">
								<tr class="inner-tables">
									<td>${article.rId}</td>
									<td>${article.rStdID}</td>
									<td>
										<c:choose>
											<c:when test="${article.rPosition eq 'student'}">
												학생
											</c:when>
											<c:otherwise>
												교직원 및 교수
											</c:otherwise>
										</c:choose>
									</td>
									<td>${article.rDepart}</td>
									<td>${article.rName}</td>
									<td class="table-title">
										${article.rTitle}
									</td>
									<td>${article.rEmail}</td>
									<td>${article.rTel}</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd" value="${article.rReg_date}"/></td>
									<td>
										<a href="reportContent.do?rId=${article.rId}">보기</a>
									</td>
									<td class="table-button">
										<form id="reportDeleteForm" name="reportDeleteForm" method="post" action="/reportmanage.do" onsubmit="return askReportDelete()">
											<input type="hidden" name="btnValue" value="delete">
											<input type="hidden" name="rId" value="${article.rId}">
											<input type="submit" value="삭제">
										</form>
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
	                    <a class="page-link" href="reportmanage.do?page=${articles.startPage-1}">이전</a>
	                </c:if>
				</li>
				
				<c:forEach var="pageNum" begin="${articles.startPage}" end="${articles.endPage}" >
				    <li class="page-item">
				    	<a class="page-link" href="reportmanage.do?page=${pageNum}">${pageNum}</a>
				    </li>
				</c:forEach>
			   
			    <li class="page-item">
			    	<c:if test="${articles.next}">
			      		<a class="page-link" href="reportmanage.do?page=${articles.endPage+1}">다음</a>
			    	</c:if>
			    </li>
			</ul>		
			<!-- 페이징 처리 끝 -->
		</div>
	</div>
</body>
</html>