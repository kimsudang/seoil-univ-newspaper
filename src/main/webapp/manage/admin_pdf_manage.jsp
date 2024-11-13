<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<%@include file="../admin/page_header.jsp" %>
<%
request.setCharacterEncoding("UTF-8");
%>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>지면 학보 관리</title>
	<link rel="stylesheet" type="text/css" href="../css/mypage.css?ser">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
	
	<script>
		function askDelete(){
			if(confirm("pdf을 삭제하시겠습니까? 삭제된 pdf는 복구가 불가능합니다.")){
				return true;
			}
			return false;
		}
	</script>
	
</head>
<script>
	window.history.forward();
	function noBack(){window.history.forward();}
</script>

<body onload="noBack();" onpageShow="if(event.persisted) noBack();" onunload="">
	<div class="page-container">
		<h2>PDF 관리</h2>
		<div class="inner-content">
			<div class="panel-body">
				<c:choose>
					<c:when test="${empty articles.messageList}">
						<p class="no-result">등록된 PDF가 없습니다.</p>
					</c:when>
					<c:otherwise>
						<table class="search-table" border="1">
							<tr class="search-title"><th>번호</th><th>제목</th><th>발행일</th><th>발행호</th><th>삭제</th></tr>
							<c:forEach var="articles" items="${articles.messageList}">
								<tr class="inner-tables">
									<td>${articles.pdf_no}</td>
									<td>
										<c:choose>
											<c:when test="${fn:length(articles.pdf_title) > 21}">
												${fn:substring(articles.pdf_title, 0, 20)}...
											</c:when>
											<c:otherwise>${articles.pdf_title}</c:otherwise>
										</c:choose>
									</td>
									<td>
										<fmt:formatDate pattern="yyyy-MM" value="${articles.publish_date}"/>
									</td>
									<td>${articles.publish_no}</td>
									<td class="table-button">
										<form id="deleteForm" name="deleteForm" method="post" action="/pdfManage.do" onsubmit="return askDelete()">
											<input type="hidden" name="btnValue" value="delete">
											<input type="hidden" name="pdf_no" value="${articles.pdf_no}">
											<input type="hidden" name="fReg_date" value="${articles.fReg_date}">
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
			<ul class="pagination justify-content-center">
				<li class="page-item">
					<c:if test="${articles.prev}">
	                    <a class="page-link" href="pdfManage.do?page=${articles.startPage-1}">이전</a>
	                </c:if>
				</li>
				
				<c:forEach var="pageNum" begin="${articles.startPage}" end="${articles.endPage}" >
				    <li class="page-item">
				    	<a class="page-link" href="pdfManage.do?page=${pageNum}">${pageNum}</a>
				    </li>
				</c:forEach>
			   
			    <li class="page-item">
			    	<c:if test="${articles.next}">
			      		<a class="page-link" href="pdfManage.do?page=${articles.endPage+1}">다음</a>
			    	</c:if>
			    </li>
			</ul>
		</div>
	</div>
</body>
</html>