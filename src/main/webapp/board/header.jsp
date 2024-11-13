<%@page import="kr.seoil.dao.BoardDAO"%>
<%@page import="java.util.ArrayList" %>
<%@page import="kr.seoil.vo.BoardVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String userSessionName = (String)session.getAttribute("userName");
	String userSessionId = (String)session.getAttribute("userStdID");
	String userSessionAdmin = (String)session.getAttribute("userAdmin");
%>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=0.5, maximum-scale=1.5, user-scalable=no" />
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../css/header.css?jf">
<script src="https://kit.fontawesome.com/ec08099fdd.js" crossorigin="anonymous"></script>

<script>
	function askLogout() {
		if(confirm("로그아웃 하시겠습니까?")){
			location.href = "../admin/logout.jsp";
		}
	}
	
	function logout() {
		location.href = "../admin/logout.jsp";
	}
	
	function displayNav() {
		const div = document.getElementById('mainMenu');
		if(div.style.display === 'none')  {
	    	div.style.display = 'block';
		}else {
	    	div.style.display = 'none';
	  	}
	}
</script>
</head>
<body>
	<header>
		<div class="inner">
			<div class="flex-inner">
				<div class="titlebar">
					<a href="/home.do" >
					<img src="../img/seoil_logo.png" alt="Logo" class="logo">
					</a>
				</div>
			</div>
			<nav id="mainMenu">
				<div class="gnb-menu">
					<ul id="gnb" class="gnb">
						<li class="mainMenuLi">
							<a class="menuLi" href="news.do?page=1&category=1">보도</a>
							<ul class="subMenu">
								<li><a class="subMenuLi longLi" href="news.do?page=1&category=8" id="subMenu">보도</a></li>
								<li><a class="subMenuLi longLi" href="news.do?page=1&category=9" id="subMenu">기획보도</a></li>
								<li><a class="subMenuLi longLi" href="news.do?page=1&category=10" id="subMenu">심층보도</a></li>
							</ul>
						</li>
						<li class="mainMenuLi"><a class="menuLi" href="news.do?page=1&category=2">서일</a></li>
						<li class="mainMenuLi"><a class="menuLi" href="news.do?page=1&category=3">기사</a></li>
						<li class="mainMenuLi"><a class="menuLi" href="news.do?page=1&category=4">인터뷰</a></li>
						<li class="mainMenuLi"><a class="menuLi" href="news.do?page=1&category=5">특집</a></li>
						<li class="mainMenuLi">
							<a class="menuLi" href="news.do?page=1&category=6">오피니언</a>
							<ul class="subMenu">
								<li><a class="subMenuLi longLi" href="news.do?page=1&category=11" id="subMenu">사설</a></li>
								<li><a class="subMenuLi longLi" href="news.do?page=1&category=12" id="subMenu">칼럼</a></li>
								<li><a class="subMenuLi longLi" href="news.do?page=1&category=13" id="subMenu">만평</a></li>
							</ul>
						</li>
						<li class="mainMenuLi">
							<a class="menuLi" href="news.do?page=1&category=7">서일학보사</a>
							<ul class="subMenu">
								<li><a class="subMenuLi longLi" href="news.do?page=1&category=14" id="subMenu">학보사 소개</a></li>
								<li><a class="subMenuLi longLi" href="news.do?page=1&category=15" id="subMenu">학보사 소식</a></li>
								<li><a class="subMenuLi longLi" href="news.do?page=1&category=16" id="subMenu">기사 제보함</a></li>
								<li><a class="subMenuLi longLi" href="news.do?page=1&category=17" id="subMenu">학생의 목소리</a></li>
							</ul>
						</li>
					</ul>
				</div>
				<div class="search-menu">
					<form class="d-flex" method="get" name="search" action="mainSearch.do">
						<div class="input-group">
						<input class="form-control" name="searchText" id="searchText" value="${param.searchText}" type="text" maxlength=255 placeholder="검색어를 입력하세요">
							<div class="input-group-append">
								<button class="search-button" type="submit">
									<i class="fa-solid fa-magnifying-glass"></i>
								</button>
							</div>
						</div>	
					</form>
				</div>
			</nav>
			<ul class="util">
			<% if(userSessionId != null) { %>
	        	<% if(userSessionAdmin.equals("0")) { %>
			        <li><p class="gnb_item s-none"><%=userSessionName%> 기자님</p></li>
			        <li><a class="gnb_item none" href="../admin/mypage_main.jsp">마이페이지</a></li>
			        <li><a class="gnb_item none" onclick="askLogout()">로그아웃</a></li>
			        <li><a class="gnb_btn" href="../admin/mypage_main.jsp"><i class="fa-solid fa-user"></i></a></li>
					<li><a class="gnb_btn" onclick="askLogout()"><i class="fa-solid fa-power-off"></i></a></li>
					<li><a class="gnb_btn" onclick="displayNav()"><i class="fa-solid fa-bars" ></i></a></li>
	        	<%} else { %>
			        <li><p class="gnb_item s-none"><%=userSessionName%> 편집장님</p></li>
			        <li><a class="gnb_item none" href="../admin/mypage_main.jsp">마이페이지</a></li>
					<li><a class="gnb_item none" onclick="askLogout()">로그아웃</a></li>
			        <li><a class="gnb_btn" href="../admin/mypage_main.jsp"><i class="fa-solid fa-user"></i></a></li>
					<li><a class="gnb_btn" onclick="askLogout()"><i class="fa-solid fa-power-off"></i></a></li>
					<li><a class="gnb_btn" onclick="displayNav()"><i class="fa-solid fa-bars" ></i></a></li>
	        	<%} %>
	        <% } else { %>
	        	<li><a class="gnb_item none" href="userLogin.do">기자 로그인</a></li>
				<li><a class="gnb_item none" href="userJoin.do">기자 회원가입</a></li>
				<li><a class="gnb_btn" href="userLogin.do"><i class="fa-solid fa-power-off"></i></a></li>
				<li><a class="gnb_btn" href="userJoin.do"><i class="fa-solid fa-user-plus"></i></a></li>
				<li><a class="gnb_btn" onclick="displayNav()"><i class="fa-solid fa-bars" ></i></a></li>
			<%} %>
			</ul>
		</div>
	</header>

</body>
</html>