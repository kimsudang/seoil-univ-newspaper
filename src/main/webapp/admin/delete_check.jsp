<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% 
	request.setCharacterEncoding("UTF-8"); 
	int result = (Integer)request.getAttribute("result");
	String userAdmin = (String)request.getAttribute("userAdmin");
%>

<script>
	window.history.forward();
	function noBack(){window.history.forward();}
</script>

<body onload="noBack();" onpageShow="if(event.persisted) noBack();" onunload="">
	<% if(result <= 0){%>
		<script>
			alert('삭제가 정상적으로 진행되지 않았습니다. 다시 진행해주세요.');
			location.href = 'userManage.do';
		</script>
	<%}else if(result > 0 && userAdmin.equals("1")) {%>
	<script>
		alert('관리자는 삭제할 수 없습니다.');
		location.href = 'userManage.do';
	</script>
	<% } else {%>
	<script>
		alert('삭제가 완료되었습니다');
		location.href = 'userManage.do';
	</script>
	<% }%>
</body>