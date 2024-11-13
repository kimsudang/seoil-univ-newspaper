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
	System.out.println(request.getAttribute("result"));
%>
<script>
	alert('기사 승인 반려가 실패하였습니다.');
	location.href='../admin/mypage_main.jsp';
</script>
<%} else{ %>
<script>
	alert('기사 승인이 반려되었습니다.');
	location.href='articleAccessManage.do?searchType=allType&searchText=';
</script>
<%} %>
</body>