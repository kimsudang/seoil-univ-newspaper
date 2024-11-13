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
	<title>회원가입 승인</title>
	<link rel="stylesheet" type="text/css" href="../css/mypage.css?sd">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
	
	<script type="text/javascript">
		function askDel(){
			if(confirm("정말 삭제하시겠습니까?")){
				return true;
			}
			return false;
		}
		
		function askAcc(){
			if(confirm("기자 가입을 승인하시겠습니까?")){
				
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
		<h2>가입 요청</h2>
		<div class="inner-content">
			<div class="panel-body">
				<c:choose>
					<c:when test="${empty articles.messageList}">
						<p class="no-result">가입 요청이 없습니다.</p>
					</c:when>
					<c:otherwise>
						<table class="search-table" border="1">
							<tr class="search-title"><th>학번</th><th>이름</th><th>메일</th><th>학과</th><th>승인</th><th>삭제</th></tr>
							<c:forEach var="tempuser" items="${articles.messageList}">
								<tr class="inner-tables">
									<td>${tempuser.tempStdID}</td>
									<td>${tempuser.tempName}</td>
									<td>${tempuser.tempEmail}</td>
									<td>${tempuser.tempDept}</td>
									<td class="table-button">
										<form id="tempAccForm" name="tempAccForm" method="post" action="tempUserManage.do" onsubmit="return askAcc()">		
											<input type="hidden" name="btnValue" value="access">
											<input type="hidden" name="stdId" value="${tempuser.tempStdID}">
											<input type="submit" value="승인">
										</form>
									</td>
									<td class="table-button">
										<form id="tempDelForm" name="tempDelForm" method="post" action="tempUserManage.do" onsubmit="return askDel()">		
											<input type="hidden" name="btnValue" value="delete">
											<input type="hidden" name="stdId" value="${tempuser.tempStdID}">
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
		                   <a class="page-link" href="tempUserManage.do?page=${articles.startPage-1}">이전</a>
		               </c:if>
				</li>
				
				<c:forEach var="pageNum" begin="${articles.startPage}" end="${articles.endPage}" >
				    <li class="page-item">
				    	<a class="page-link" href="tempUserManage.do?page=${pageNum}">${pageNum}</a>
				    </li>
				</c:forEach>
			   
			    <li class="page-item">
			    	<c:if test="${articles.next}">
			      		<a class="page-link" href="tempUserManage.do?page=${articles.endPage+1}">다음</a>
			    	</c:if>
			    </li>
			</ul>	
		</div>
	</div>
</body>
</html>