<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<% 
request.setCharacterEncoding("utf-8"); 

%>
<!DOCTYPE HTML>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	
	<title>서일대학교 학보사</title>
	<link rel="stylesheet" type="text/css" href="../css/index.css?fd">
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://kit.fontawesome.com/ec08099fdd.js" crossorigin="anonymous"></script>
	<script src="//cdn.ckeditor.com/4.19.0/full/ckeditor.js"></script>
</head>
<body>
<div class="user-wrap">
	<div class="wrap">
		<div class="body-content">
			<div class="wrap-section">
			<section>
				<header class="article-content-header">
						<h4 class="news-category">기사 제보함</h4>
						<h3 class="news-title">기사 제보함</h3>
				</header>
				<div class="inner-content">
					<div class="wrap-news">	
						<div class="wrap-news-article">
							<section id="articleViewContent" class="article-view-content">
								<article class="article-body">
									<div class="article-content-body">
										<article id="article-view-content-body">
											<form name="reportForm" method="post"  action="/userReport.do">
												<ul class="reportUl">
													<li>
														<label for="reportSelect" class="reportTitle">1. 해당 사항을 선택해주세요.</label>																						
    													<input type="radio" id="reportSelect" name="reportSelect" class="input" value="employee" > 교직원 및 교수
    													<input type="radio" id="reportSelect" name="reportSelect" class="input" value="student" required> 학생
													</li>
													<li>
														<label for="reportDepart" class="reportTitle">2. 소속 (예: 학생지원처, xxx학과)<span id="deptChk"></span></label>
    													<input type="text" id="reportDepart" name="reportDepart" class="input" placeholder="귀하의 소속을 입력해 주세요." maxlength=20 required>
													</li>
													<li>
														<label for="reportName" class="reportTitle">3. 이름<span id="nameChk"></span></label>
    													<input type="text" id="reportName" name="reportName" class="input" placeholder="귀하의 이름을 입력해 주세요." maxlength=10 required>
													</li>
													<li>
														<label for="reportStdId" class="reportTitle">4. 학번 <span id="idChk"></span></label>
    													<input type="text"  id="reportStdId" name="reportStdId" class="input" placeholder="귀하의 학번을 입력해주세요."  maxlength="9" required>	
													</li>
													<li>
														<label for="reportEmail" class="reportTitle">5. 이메일 (예 : aaa@bbb.ccc)<span id="emailChk"></span></label>
    													<input type="email" id="reportEmail" name="reportEmail" class="input" placeholder="귀하의 이메일을 입력해 주세요." maxlength=50 required>
													</li>
													<li>
														<label for="reportTel" class="reportTitle">6. 연락처 (예 : 02-xxx-xxxx, 010-xxxx-xxxx)<span id="telChk"></span></label>
														<input type="text" name="reportTel" id="reportTel"  class="input" placeholder="010-xxxx-xxxx" pattern="[0-9]{2,3}-[0-9]{3,4}-[0-9]{3,4}" maxlength="13" required>
													</li>
													<li>
														<label for="reportContentTitle" class="reportTitle">7. 제목 <span id="titleChk"></span></label>
														<input type="text" id="reportContentTitle" name="reportContentTitle" class="input" placeholder="제목을 입력해 주세요." maxlength=50 required>
													</li>
													<li>
														<label for="reportContent" class="reportTitle">8. 제보 내용 <span id="contentChk"></span></label>
														<textarea name="reportContent" id="reportContent" class="reportContent" placeholder="제보하실 내용에 대해 입력해주세요." required></textarea>
													</li>
												</ul>
												<p><b>서일대학교(이하 “본교”)에서는 게시판과 관련해 개인정보를 수집 및 활용하고 있습니다.<br>
												1. 개인정보 수집 및 이용 목적 : 게시판 서비스 제공을 위한 개인정보 수집<br>
												2. 개인정보 수집 항목 : 성명, 이메일, 비밀번호(확인용 번호), 연락처(취재 여부 전달용)<br>
												3. 개인정보 보유기간 : 게시물 삭제 시<br>
												4. 개인정보 수집 및 이용 동의 거부 권리 정보주체는 개인정보 수집 및 이용 동의에 대해 거부할 권리가 있으며,<br>
												동의를 거부하는 경우에는 게시판 서비스를 이용하실 수 없습니다.<br>
												</b></p>
												<input type="submit" class="btn btn-primary form-control" id="report-btn" value="제보하기" onclick="if(!confirm('기사를 제보하시겠습니까?\n기입하신 메일과 연락처로 추후 취재 여부에 대해 연락드리겠습니다.')){return false;}">
											</form>
										</article>
									</div>
								</article>
							</section>
						</div>		
					</div>
					<%@ include file="wrap_side.jsp" %>
				</div>
			</section>
			</div>
		</div>
	</div>
	<%@ include file="footer.jsp" %>
</div>

<script>
$(function(){
	var getIdCheck= RegExp(/^[0-9]{8,9}$/);
	var getName= RegExp(/^[가-힣]{2,10}$/);
	var getMail = RegExp(/^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/);
	var getTel = RegExp(/^[0-9]{2,3}-[0-9]{3,4}-[0-9]{3,4}$/);
	var chk1 = false, chk2 = false, chk3 = false, chk4 = false,  chk5 = false, chk6 = false, ch7=false;
	
	//회원가입 검증~~
	//ID 입력값 검증.
	$('#reportStdId').on('keyup', function() {
		//학번 공란인가
		if($("#reportStdId").val() == ""){
			$('#reportStdId').css("background-color", "pink");
			$('#idChk').html('<b style="font-size:8px;color:red;">[학번은 필수 정보입니다.]</b>');
			chk1 = false;
		}
		
		//학번 유효성검사
		else if(!getIdCheck.test($("#reportStdId").val())){
			$('#reportStdId').css("background-color", "pink");
			$('#idChk').html('<b style="font-size:8px;color:red;">[학번은 8자리 또는 9자리입니다. 다시 확인해주세요.]</b>');	  
			chk1 = false;
		} else {
			$('#reportStdId').css("background-color", "#CCE1FF");
			$('#idChk').html('');
			chk1 = true;
		} 
	});
	
	//이름 입력값 검증.
	$('#reportName').on('keyup', function() {
		//이름값 공백 확인
		if($("#reportName").val() == ""){
		    $('#reportName').css("background-color", "pink");
			$('#nameChk').html('<b style="font-size:8px;color:red;">[이름은 필수 정보입니다.]</b>');
			chk2 = false;
		}		         
		//이름값 유효성검사
		else if(!getName.test($("#reportName").val())){
		    $('#reportName').css("background-color", "pink");
			$('#nameChk').html('<b style="font-size:8px;color:red;">[두 글자 이상, 열 글자 이하의 한글로 작성해주세요.]</b>');
			chk2 = false;
		} else {
			$('#reportName').css("background-color", "#CCE1FF");
			$('#nameChk').html('');
			chk2 = true;
		}
		
	});
	
	$('#reportEmail').on('keyup', function() {
		//이메일값 공백 확인
		if($("#reportEmail").val() == ""){
		    $('#reportEmail').css("background-color", "pink");
			$('#emailChk').html('<b style="font-size:8px;color:red;">[이메일은 필수 정보입니다.]</b>');
			chk3 = false;
		}		         
		//이메일값 유효성검사
		else if(!getMail.test($("#reportEmail").val())){
		    $('#reportEmail').css("background-color", "pink");
			$('#emailChk').html('<b style="font-size:8px;color:red;">[이메일 형식으로 입력해주세요.]</b>');
			chk3 = false;
		} else {
			$('#reportEmail').css("background-color", "#CCE1FF");
			$('#emailChk').html('');
			chk3 = true;
		}
		
	});
	
	$('#reportDepart').on('keyup', function() {
		//학과값 공백 확인
		if($("#reportDepart").val() == ""){
		    $('#reportDepart').css("background-color", "pink");
			$('#deptChk').html('<b style="font-size:8px;color:red;">[소속은 필수 정보입니다.]</b>');
			chk4 = false;
		}else {
			$('#reportDepart').css("background-color", "#CCE1FF");
			$('#deptChk').html('');
			chk4 = true;
		}
		
	});
	
	$('#reportTel').on('keyup', function() {
		//연락처 공백 확인
		if($("#reportTel").val() == ""){
		    $('#reportTel').css("background-color", "pink");
			$('#telChk').html('<b style="font-size:8px;color:red;">[연락처는 필수 정보입니다.]</b>');
			chk5 = false;
		}		         
		//연락처 유효성검사
		else if(!getTel.test($("#reportTel").val())){
		    $('#reportTel').css("background-color", "pink");
			$('#telChk').html('<b style="font-size:8px;color:red;">[연락처 형식으로 입력해주세요.]</b>');
			chk5 = false;
		} else {
			$('#reportTel').css("background-color", "#CCE1FF");
			$('#telChk').html('');
			chk5 = true;
		}
		
	});
	
	$('#reportContent').on('keyup', function() {
		//내용 공백 확인
		if($("#reportContent").val() == ""){
			$('#contentChk').html('<b style="font-size:8px;color:red;">[제보 내용은 필수 정보입니다.]</b>');
			chk6 = false;
		}else {
			$('#contentChk').html('');
			chk6 = true;
		}
		
	});
	
	$('#reportContentTitle').on('keyup', function() {
		//제목 공백 확인
		if($("#reportContentTitle").val() == ""){
		    $('#reportContentTitle').css("background-color", "pink");
			$('#titleChk').html('<b style="font-size:8px;color:red;">[제목은 필수 정보입니다.]</b>');
			chk7 = false;
		}else {
			$('#reportContentTitle').css("background-color", "#CCE1FF");
			$('#titleChk').html('');
			chk7 = true;
		}
		
	});

});

</script>
</body>
</html>