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
int result = (Integer)request.getAttribute("result");
if(result <= 0){
%>
<script>
	alert('기사 수정 상태로 바꾸는데 문제가 있습니다.');
	location.href = 'articleManage.do';
</script>

<%} else { %>	
<script>
	alert('기사를 수정할 수 있습니다.');
	location.href = 'articleManage.do';
</script>
<%} %>
</body>