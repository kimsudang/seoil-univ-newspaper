<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<% request.setCharacterEncoding("utf-8"); %>
	<% if(request.getAttribute("error") != null) { %>
	<script>
		alert('입력칸 중 공란이 있습니다.');
		history.back();
	</script>
	
	<%
		} // 키값 수정
	if(request.getAttribute("tempStdID") != null && (Integer)request.getAttribute("result") >0) {
	%>
	<script>
		alert('회원가입이 요청되었습니다. 관리자의 승인 후 이용가능합니다.');
		location.href = '/home.do';
	</script>
	<%
		}else if((Integer)request.getAttribute("result") <= 0){%>
	<script>
		alert('회원가입이 정상적으로 진행되지 않았습니다. 값을 확인해 다시 진행해주세요.');
		history.back();
	</script>
	<%		
		}
	%>