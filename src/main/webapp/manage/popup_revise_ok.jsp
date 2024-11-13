<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="kr.seoil.dao.BoardDAO" %>
<% 
request.setCharacterEncoding("UTF-8"); 
%>
<script>
	window.history.forward();
	function noBack(){window.history.forward();}
</script>

<body onload="noBack();" onpageShow="if(event.persisted) noBack();" onunload="">
<%
int result = (Integer)request.getAttribute("result");
if(result <= 0){
%>
<script>
	alert('팝업 수정을 실패했습니다. 다시 시도해 주세요.');
	location.href = 'popupmanage.do';
</script>

<%} else { %>	
<script>
	alert('팝업 수정이 완료되었습니다.');
	location.href = 'popupmanage.do';
</script>
<%} %>
</body>