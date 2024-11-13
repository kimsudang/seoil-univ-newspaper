<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
request.setCharacterEncoding("UTF-8");
if((int)(request.getAttribute("result")) <= 0) {
%>
<script>
	alert('광고 승인을 실패하였습니다.');
	location.href='advertiseManage.do';
</script>
<%} else { %>
<script>
	alert('광고 승인이 성공적으로 완료되었습니다.');
	location.href='advertiseManage.do';
</script>
<%} %>