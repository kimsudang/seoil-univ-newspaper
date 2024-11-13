<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% 
	request.setCharacterEncoding("UTF-8");
%>
<html>
<head>
	<meta charset="UTF-8">
	<title>기자관리</title>
	<link rel="stylesheet" type="text/css" href="../css/mypage.css?sr">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
	
	<script type="text/javascript">
		function askSure(){
			if(confirm("권한을 위임하시겠습니까?")){
				return true;
			}
			return false;
		}
		
		function askDel(){
			if(confirm("정말 삭제하시겠습니까?")){
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
	<%@include file="../admin/page_header.jsp" %>
	<div class="page-container">
		<h2>기자 목록</h2>
		<div class="inner-content">
			<div class="panel-body">
				<c:choose>
					<c:when test="${empty articles.messageList}">
						<p class="no-result">불러올 기자 목록이 없습니다.</p>
					</c:when>
					<c:otherwise>
						<table class="search-table" border="1">
							<tr class="search-title"><th>권한 위임</th><th>권한</th><th>학번</th><th>이름</th><th>메일</th><th>학과</th><th>삭제</th></tr>
							<c:forEach var="user" items="${articles.messageList}">
								<tr class="inner-tables">
									<td class="table-button">
										<c:if test="${user.userAdmin eq '0'}">
										<form id="askSureForm" name="askSureForm" method="post" action="userManage.do" onsubmit="return askSure()">
											<input type="hidden" name="btnValue" value="entrust">
											<input type="hidden" name="stdId" value="${user.userStdID}">
											<input type="hidden" name="userAdmin" value="${user.userAdmin}">
											<input type="submit" value="권한 위임">
										</form></c:if>
									</td>
									<td>
										<c:choose>
											<c:when test="${user.userAdmin eq '1'}">관리자</c:when>
											<c:otherwise>기자</c:otherwise>
										</c:choose>
									</td>
									<td>${user.userStdID}</td>
									<td>${user.userName}</td>
									<td>${user.userEmail}</td>
									<td>${user.userDept}</td>
									<td class="table-button">
										<form id="resultDelForm" name="resultDelForm" method="post" action="userManage.do" onsubmit="return askDel()">		
											<input type="hidden" name="btnValue" value="delete">
											<input type="hidden" name="stdId" value="${user.userStdID}">
											<input type="hidden" name="userAdmin" value="${user.userAdmin}">
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
		                   <a class="page-link" href="userManage.do?page=${articles.startPage-1}">이전</a>
		               </c:if>
				</li>
				
				<c:forEach var="pageNum" begin="${articles.startPage}" end="${articles.endPage}" >
				    <li class="page-item">
				    	<a class="page-link" href="userManage.do?page=${pageNum}">${pageNum}</a>
				    </li>
				</c:forEach>
			   
			    <li class="page-item">
			    	<c:if test="${articles.next}">
			      		<a class="page-link" href="userManage.do?page=${articles.endPage+1}">다음</a>
			    	</c:if>
		    	</li>
			</ul>	
		</div>
	</div>
	
</body>
</html>