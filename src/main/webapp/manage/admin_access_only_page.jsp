<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
	request.setCharacterEncoding("UTF-8"); 
	int result = (Integer)request.getAttribute("result");
%>
<script>
	window.history.forward();
	function noBack(){window.history.forward();}
</script>

<body onload="noBack();" onpageShow="if(event.persisted) noBack();" onunload="">
	<% if(result == -1){%>
		<script>
			alert('로그인 없이 접근할 수 없습니다.');
			location.href = 'home.do';
		</script>
	<% }%>
</body>