<%@ page import="kr.seoil.service.TempService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String stdId = request.getParameter("tempStdID");
	TempService service = TempService.getInstance();

	boolean flag = service.confirmId(stdId);
	String str = "";
	
	if (flag) { //ID중복됨
		str = "NO";
		out.print(str);
		System.out.println("ID중복!!");
	} else {
		str = "YES";
		out.print(str);
		System.out.println("ID사용 가능!!");
	
	}
%>
