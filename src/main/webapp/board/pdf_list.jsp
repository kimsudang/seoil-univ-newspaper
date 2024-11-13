<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<% 
request.setCharacterEncoding("utf-8");
%>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>서일대학교 학보사</title>
<link rel="stylesheet" type="text/css" href="../css/index.css?jcs">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://kit.fontawesome.com/ec08099fdd.js" crossorigin="anonymous"></script>

</head>
<body>
<div class="user-wrap">
	<div class="wrap">
		<div class="body-content">
			<div class="wrap-section">
				<div class="inner-content">
					<div class="panel-body">
						<h2>학보 pdf 다운로드</h2>
						<div class="empty"></div>
						<ul>
							<c:forEach var="articles" items="${articles.messageList}">
								<li class="pdf">
									<div class="pdf-list">
										<a href="pdfViewList.do?pdf=${articles.pdf_no}&publish=${articles.publish_no}" target="_blank">
											<i class="fa-regular fa-file-pdf"></i>
											<fmt:formatDate pattern="yyyy-MM" value="${articles.publish_date}"/>
											${articles.publish_no}호
										</a>
									</div>
								</li>
							</c:forEach>	
						</ul>
					</div>
					<%@ include file="wrap_side.jsp" %>
				</div>
			</div>
		</div>
		<div class="empty"></div>
		<ul class="pagination justify-content-center">
			<li class="page-item">
				<c:if test="${articles.prev}">
                    <a class="page-link" href="pdfViewList.do?page=${articles.startPage-1}">이전</a>
                </c:if>
			</li>
			
			<c:forEach var="pageNum" begin="${articles.startPage}" end="${articles.endPage}" >
			    <li class="page-item">
			    	<a class="page-link" href="pdfViewList.do?page=${pageNum}">${pageNum}</a>
			    </li>
			</c:forEach>
		   
		    <li class="page-item">
		    	<c:if test="${articles.next}">
		      		<a class="page-link" href="pdfViewList.do?page=${articles.endPage+1}">다음</a>
		    	</c:if>
		    </li>
		</ul>
	</div>
	<%@ include file="footer.jsp" %>
</div>
</body>
</html>