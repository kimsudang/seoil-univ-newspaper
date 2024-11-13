<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
request.setCharacterEncoding("UTF-8");
%>
<script>
	window.history.forward();
	function noBack(){window.history.forward();}
</script>

<body onload="noBack();" onpageShow="if(event.persisted) noBack();" onunload="">
<%
if((int)(request.getAttribute("result")) <= 0) {
%>
<script>
	alert('대표 기사 지정을 실패하였습니다.');
	location.href='articleManage.do';
</script>
<%} else { %>
<script>
	alert('대표 기사 지정이 완료되었습니다.');
	location.href='articleManage.do';
</script>
<% } %>
</body>