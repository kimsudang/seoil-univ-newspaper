<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<% request.setCharacterEncoding("utf-8"); %>

<script>
	window.history.forward();
	function noBack(){window.history.forward();}
</script>

<body onload="noBack();" onpageShow="if(event.persisted) noBack();" onunload="">
<% if((int)(request.getAttribute("result")) >0) { %>
<script>
	alert('팝업 등록이 정상적으로 수행되었습니다.\n팝업 관리란에서 등록하신 팝업 창을 관리할 수 있습니다.');
	location.href = '../admin/mypage_main.jsp';
	
</script>

<%
	} // 키값 수정
else if((int)(request.getAttribute("result")) <= 0) { %>
<script>
	alert('팝업 등록이 정상적으로 수행되지 않았습니다. 다시 시도해 주세요');
	history.back();
</script>
<%
	}
%>
</body>