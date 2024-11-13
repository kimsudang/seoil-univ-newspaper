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
	alert('pdf 삭제를 실패하였습니다. 다시 시도해 주세요.');
	location.href='pdfManage.do';
</script>
<%} else{ %>
<script>
	alert('pdf 삭제가 완료되었습니다.');
	location.href='pdfManage.do';
</script>
<%} %>
</body>