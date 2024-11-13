<%@page import="kr.seoil.vo.UserVO"%>
<%@page import="kr.seoil.service.UserService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%

	//입력한 값 가져옴
	String stdId = (String)request.getAttribute("userStdID");
	String passwd = (String)request.getAttribute("userPW");
	String already = (String)request.getAttribute("already");
	System.out.println(already);
	UserService service = UserService.getInstance();
	String str = "";
	
	if (already !=null && already.equals("error")) {%>
	<script>
		alert('이미 로그인 되어있어 홈으로 이동합니다.');
		location.href = "../home.do";
	</script><%
	}
	else{
		int result = service.userCheck(stdId, passwd);
		if (result == 1) {
			UserVO user = service.getUserInfo(stdId);
			session.setAttribute("userStdID", stdId);
			session.setAttribute("userName", user.getUserName());
			session.setAttribute("userAdmin", user.getUserAdmin());
			response.sendRedirect("../home.do");
			str = "LOGIN_OK";
		} else if (result <= 0) {
			str = "NOT_PW"; %>
		<script>
			alert('존재하지 않거나 올바르지 않은 정보를 입력했습니다.');
			location.href = "../userLogin.do";
		</script>
		<%	
		} else {
			str = "NOT_ID"; %>
		<script>
			alert('존재하지 않거나 올바르지 않은 정보를 입력했습니다.');
			location.href = "../userLogin.do";
		</script>
		<%}
	}
%>