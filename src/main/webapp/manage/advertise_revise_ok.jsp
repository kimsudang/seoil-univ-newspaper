<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<% 
request.setCharacterEncoding("UTF-8");
if((int)(request.getAttribute("result")) <= 0) {
%>
<script>
	alert('광고 수정을 진행할 수 없습니다.');
	location.href='../admin/mypage_main.jsp';
</script>
<%} else{ %>
<script>
	alert('광고를 수정해주시기 바랍니다.');
	location.href='advertiseupdateupload.do';
</script>
<%} %>