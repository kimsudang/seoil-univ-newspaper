<!-- 마이페이지 헤더 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%	
	request.setCharacterEncoding("UTF-8");
	String userSessionName = (String)session.getAttribute("userName");
	String userSessionId = (String)session.getAttribute("userStdID");
	String userSessionAdmin = (String)session.getAttribute("userAdmin");
	if(userSessionId == null) { %>
	<script>
		alert("로그인 없이 접근할 수 없습니다.");
		location.href = "/home.do";
	</script>
<%} %>
	
	<script>
		function askLogout() {
			if(confirm("로그아웃 하시겠습니까?")){
				location.href = "logout.jsp";
			}
		}
	
		function goEdit(){
			location.href ="../contentupload.do";
		}	
		
		function displayNav() {
			const div = document.getElementById('menu-nav');
			if(div.style.display === 'none')  {
		    	div.style.display = 'block';
			}else {
		    	div.style.display = 'none';
		  }
		} 
		
		function page_move(page, id){
			var f = document.myShow;
			f.page.value = page;
			f.id.value = id;
			f.action="/mypageManage.do?page=1&searchType=allType&searchText=";//이동할 페이지
		    f.method="post";
		    f.submit();
		}
	</script>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<link rel="stylesheet" type="text/css" href="../css/myheader.css?hgf">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://kit.fontawesome.com/ec08099fdd.js" crossorigin="anonymous"></script>
	
</head>
<body>
	<div class="head">
		<div class="titlebar">
			<a href="../board/index.jsp" >
			<img src="../img/seoil_logo.png" alt="Logo" class="logo">
			</a>
		</div>
		
		
		<div class="d-flex flex-row justify-content-end">
		<% if(userSessionId != null) { %>
			<button type="button" class="icon" onclick="goEdit()"><i class="fa-solid fa-pen-to-square"></i></button>
			<button type="button" class="btn btn-primary form-control" id="write-btn" onclick="goEdit()">기사작성</button>
			<button type="button" class="icon" onclick="askLogout()"><i class="fa-solid fa-power-off"></i></button>
			<button type="button" class="btn btn-primary form-control" id="signout-btn" onclick="askLogout()">로그아웃</button>
			<button type="button" class="icon" onclick="displayNav()"><i class="fa-solid fa-bars" ></i></button>
		<% } %>
		</div>
	</div>
	
	<nav id="menu-nav">
		<c:choose>
			<c:when test="${userAdmin == '1'}">
				<ul class="menu">
					<li>
				        <a href="#">기자 관리</a>
				        <ul class="submenu">
				           	<li><a class="dropdown-item" href="../tempUserManage.do">가입 승인</a></li>
							<li><a class="dropdown-item" href="../userManage.do">기자 관리</a></li>
				        </ul>
				   	</li>
				   	<li>
				        <a href="#">내 기사 관리</a>
				        <ul class="submenu">
				           	<li>
				           	<form name="myShow" class="myShow">
					    		<input type="hidden" name="page" />
					    		<input type="hidden" name="id" />
					    		<a class="dropdown-item" href="javascript:page_move('1', '${userSessionId}');">기사 관리</a>
					    	</form></li>
				        </ul>
				   	</li>
				   	<li>
				        <a href="#">기사 관리</a>
				        <ul class="submenu">
					        <li><a class="dropdown-item" href="../articleAccessManage.do?page=1&searchType=allType&searchText=">미승인 기사 관리</a></li>
						    <li><a class="dropdown-item" href="../articleManage.do?page=1&searchType=allType&searchText=">승인 기사 관리</a></li>
							<li><a class="dropdown-item" href="../reportmanage.do?page=1">기사 제보 관리</a></li>
				        </ul>
				   	</li>
				   	<li>
				        <a href="#">페이지 등록</a>
				        <ul class="submenu">
				           	<li><a class="dropdown-item" href="../advertiseupload.do">광고 등록</a></li>
							<li><a class="dropdown-item" href="../popupRegister.do">팝업 등록</a></li>
							<li><a class="dropdown-item" href="../pdfRegister.do">지면 학보 등록</a></li>
				        </ul>
				   	</li>
				   	<li>
				        <a href="#">페이지 관리</a>
				        <ul class="submenu">
				            <li><a class="dropdown-item" href="../advertiseManage.do?page=1&category=advertise">광고 관리</a></li>
							<li><a class="dropdown-item" href="../popupmanage.do">팝업 관리</a></li>
							<li><a class="dropdown-item" href="../pdfManage.do">지면 학보 관리</a></li>
				        </ul>
				   	</li>
				</ul>
			</c:when>
			<c:otherwise>
				<ul class="menu">
					<li>
				        <a href="#">내 기사 관리</a>
				        <ul class="submenu">
				           	<li>
				           	<form name="myShow" class="myShow">
					    		<input type="hidden" name="page" />
					    		<input type="hidden" name="id" />
					    		<a class="dropdown-item" href="javascript:page_move('1', '${userSessionId}');">기사 관리</a>
					    	</form></li>
				        </ul>
				   	</li>
				</ul>
			</c:otherwise>
		</c:choose>
	</nav>
</body>
</html>