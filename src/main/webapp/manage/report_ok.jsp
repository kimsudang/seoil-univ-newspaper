<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<% request.setCharacterEncoding("utf-8"); %>
	<% if(request.getAttribute("error") != null) { %>
	<script>
		alert('입력칸 중 공란이 있습니다.');
		history.back();
	</script>
	
	<%
		} // 키값 수정
	if((Integer)request.getAttribute("result") >0) {
	%>
	<script>
		alert('기사 제보가 완료되었습니다! 제보해주셔서 감사드립니다.');
		location.href = '/home.do';
	</script>
	<%
		}else if((Integer)request.getAttribute("result") <= 0){%>
	<script>
		alert('기사 제보에 실패했습니다. 다시 시도해주세요.');
		history.back();
	</script>
	<%		
		}
	%>