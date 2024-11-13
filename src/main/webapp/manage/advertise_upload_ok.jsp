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
if((int)(request.getAttribute("result")) <=0) { %>
<script>
	alert('광고 등록이 정상적으로 진행되지 않았습니다. 다시 등록해주세요.');
	location.href ="advertiseupload.do";
	
</script>			
<% 		
}else{%>
<script>
	alert('광고 등록이 완료되었습니다.');
	location.href ="../admin/mypage_main.jsp";
</script>

<%} %>
</body>
