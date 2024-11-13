<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@include file="../admin/page_header.jsp" %>
<%@page import="kr.seoil.vo.BoardVO" %>

<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>팝업 관리</title>
	<link rel="stylesheet" type="text/css" href="../css/mypage.css?ater">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
	
	<script>
		function askAdvertiseDelete(){
			if(confirm("팝업을 삭제하시겠습니까? 삭제된 팝업은 복구가 불가능합니다.")){
				return true;
			}
			return false;
		}
		
		function askAdvertiseRevise(){
			if(confirm("수정하시는 경우 이미지 파일의 재등록이 필요합니다.\n수정하시겠습니까?")){
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
		<h2>팝업 관리</h2>
		<div class="inner-content">
			<div class="panel-body">
				<c:choose>
					<c:when test="${empty articles}">
						<p class="no-result">등록된 팝업이 없습니다.</p>
					</c:when>
					<c:otherwise>
						<table class="search-table" border="1">
							<tr class="search-title">
								<th>번호</th><th>사진</th><th>팝업 제목</th><th>이동할 게시글 url</th><th>시작일</th><th>종료일</th><th>페이지 상단에서 좌표</th><th>페이지 좌측에서 좌표</th><th>수정</th><th>삭제</th>
							</tr>
							<c:forEach var="articles" items="${articles}">
				
							<tr class="inner-tables">
								<td>${articles.pop_no}</td>
								<td>
									<c:if test="${not empty articles.pop_img_path}" var="imageShow">
										<img src="${articles.pop_img_path}" style="width: 150px; height:100px;">
									</c:if>
								</td>
								
								<!-- 제목 미리보기 -->
								<td>
									<c:choose>
										<c:when test="${fn:length(articles.pop_title) > 26}">
											${fn:substring(articles.pop_title, 0, 25)}...
										</c:when>
										<c:otherwise>${articles.pop_title}</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:choose>
										
										<c:when test="${fn:length(articles.pop_url) > 26}">
											<a href="${articles.pop_url}">${fn:substring(articles.pop_url, 0, 25)}...</a>
										</c:when>
										<c:otherwise><a href="${articles.pop_url}">${articles.pop_url}</a></c:otherwise>
										
									</c:choose>
								</td>
								<td>
									<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${articles.start_date}"/>
								</td>
								<td>
									<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${articles.end_date}"/>
								</td>
								<td>
									${articles.pop_loc_top}
								</td>
								<td>
									${articles.pop_loc_left}
								</td>
								<td class="table-button">
									<form id="reviseArticleForm" name="reviseArticleForm" method="post" action="/popupupdateupload.do" onsubmit="return askAdvertiseRevise()"> 
										<input type="hidden" name="btnValue" value="revise">
										<input type="hidden" name="pop_no" value="${articles.pop_no}">
										<input type="hidden" name="pReg_date" value="${articles.pReg_date}">
										<input type="submit" value="수정">
									</form>
								</td>
								<td class="table-button">
									<form id="advertiseDeleteForm" name="advertiseDeleteForm" method="post" action="/popupmanage.do" onsubmit="return askAdvertiseDelete()">
										<input type="hidden" name="btnValue" value="delete">
										<input type="hidden" name="pop_no" value="${articles.pop_no}">
										<input type="hidden" name="pReg_date" value="${articles.pReg_date}">
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
	</div>
</body>
</html>