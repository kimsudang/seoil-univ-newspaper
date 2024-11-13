<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
	window.history.forward();
	function noBack(){window.history.forward();}
</script>
<body onload="noBack();" onpageShow="if(event.persisted) noBack();" onunload="">
<c:choose>
	<c:when test="${empty userStdID}">
		<script>
			alert("이미 로그아웃 되었습니다.");
			location.href="/home.do";
		</script>
	</c:when>
	<c:otherwise>
		<%session.invalidate(); %>
		<script>
		alert("로그아웃 되었습니다.");
		location.href="/home.do";
		</script>
	</c:otherwise>
</c:choose>
</body>	
