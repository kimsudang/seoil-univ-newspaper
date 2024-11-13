<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="../css/login.css?aer" rel="stylesheet" type="text/css">

<title>회원가입 - 서일학보</title>
</head>
<body>	
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
	<div class="container col-lg-5">
		<div class ="title">
			<a href="../board/index.jsp" >
				<img src="../img/seoil_logo.png" alt="Logo" class="logo">
			</a>
		</div>
		<div class="loginform">
			<div class="jumbotron">
				<form id="signUpForm" method="post" action="/userJoin.do">
					<p class="join">회원가입</p>		
					<div class="form-group">
						<p class="else">학번<span id="idChk"></span></p>
						<input type="text" class="form-control" placeholder="학번" name="tempStdID" id="user_stdID" maxlength="9" required>			
					</div>		
							
					<div class="form-group">
						<p class="else">비밀번호<span id="pwChk"></span></p>
						<input type="password" class="form-control" placeholder="비밀번호" name="tempPW" id="user_pw" minlength="8" maxlength="30" required>			
					</div>
					
					<div class="form-group">
						<p class="else">비밀번호 확인<span id="pwChk2"></span></p>
						<input type="password" class="form-control" placeholder="비밀번호" name="tempPWCheck" id="userPW_check" minlength="8" maxlength="30" required>			
					</div>
					
					<div class="form-group">
						<p class="else">이름<span id="nameChk"></span></p>
						<input type="text" class="form-control" placeholder="이름" name="tempName" id="user_name" maxlength="10" required>			
					</div>
					
					<div class="form-group">			
						<p class="else">이메일<span id="emailChk"></span></p>
						<input type="email" class="form-control" placeholder="이메일" name="tempEmail" id="user_email" maxlength="50" required>			
					</div>
										
					<div class="form-group">
						<p class="else">학과<span id="deptChk"></span></p>
						<input type="text" class="form-control" placeholder="학과" name="tempDept" id="user_dept" maxlength="20" required>		
					</div>
					<input type="submit" class="btn btn-primary form-control" id="signup-btn" value="회원가입">
				</form>
			</div>
		</div>
		
		<div class="footer">
		</div>
	</div>
	
	<script>
	$(function(){
		var getIdCheck= RegExp(/^[0-9]{9}$/);
		var getPwCheck= RegExp(/([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])/);
		var getName= RegExp(/^[가-힣]{2,10}$/);
		var getMail = RegExp(/^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/);
		var chk1 = false, chk2 = false, chk3 = false, chk4 = false,  chk5 = false, chk6 = false;
		
		//회원가입 검증~~
		//ID 입력값 검증.
		$('#user_stdID').on('keyup', function() {
			//학번 공란인가
			if($("#user_stdID").val() == ""){
				$('#user_stdID').css("background-color", "pink");
				$('#idChk').html('<b style="font-size:8px;color:red;">[학번은 필수 정보입니다.]</b>');
				chk1 = false;
			}
			
			//학번 유효성검사
			else if(!getIdCheck.test($("#user_stdID").val())){
				$('#user_stdID').css("background-color", "pink");
				$('#idChk').html('<b style="font-size:8px;color:red;">[학번은 9자리입니다. 다시 확인해주세요.]</b>');	  
				chk1 = false;
			} 
			//ID 중복확인 비동기 처리
			else {
				$.ajax({
					type: "POST",
					url: "confirm_id.jsp",
					data: {
						"tempStdID": $('#user_stdID').val()
					},
					success: function(data) {
						if($.trim(data) == "YES") {
							$('#user_stdID').css("background-color", "#CCE1FF");
							$('#idChk').html('<b style="font-size:8px;color:green;">[사용 가능한 ID입니다.]</b>');
							chk1 = true;
						} else {
							$('#user_stdID').css("background-color", "pink");
							$('#idChk').html('<b style="font-size:8px;color:red;">[중복된 ID입니다.]</b>');
							chk1 = false;
						}
					}
				});
			}
		});
		
		//패스워드 입력값 검증.
		$('#user_pw').on('keyup', function() {	 
			//비밀번호 공백 확인
			if($("#user_pw").val() == ""){
			    $('#user_pw').css("background-color", "pink");
				$('#pwChk').html('<b style="font-size:8px;color:red;">[패스워드는 필수 정보입니다.]</b>');
				chk2 = false;
			}	
			//비밀번호 유효성검사
			else if(!getPwCheck.test($("#user_pw").val()) || $("#user_pw").val().length < 8 || $("#user_pw").val().length > 30){
			    $('#user_pw').css("background-color", "pink");
				$('#pwChk').html('<b style="font-size:8px;color:red;">[특수문자 포함 8자이상, 30자 이하로 입력해주세요.]</b>');
				chk2 = false;
			} else {
				$('#user_pw').css("background-color", "#CCE1FF");
				$('#pwChk').html('');
				chk2 = true;
			}
			
		});
		
		//패스워드 확인란 입력값 검증.
		$('#userPW_check').on('keyup', function() {
			//비밀번호 확인란 공백 확인
			if($("#userPW_check").val() == ""){
			    $('#userPW_check').css("background-color", "pink");
				$('#pwChk2').html('<b style="font-size:8px;color:red;">[패스워드를 확인해주세요.]</b>');
				chk3 = false;
			}		         
			//비밀번호 확인란 유효성검사
			else if($("#user_pw").val() != $("#userPW_check").val()){
			    $('#userPW_check').css("background-color", "pink");
				$('#pwChk2').html('<b style="font-size:8px;color:red;">[패스워드가 일치하지 않습니다.]</b>');
				chk3 = false;
			} else {
				$('#userPW_check').css("background-color", "#CCE1FF");
				$('#pwChk2').html('');
				chk3 = true;
			}
			
		});
		
		//이름 입력값 검증.
		$('#user_name').on('keyup', function() {
			//이름값 공백 확인
			if($("#user_name").val() == ""){
			    $('#user_name').css("background-color", "pink");
				$('#nameChk').html('<b style="font-size:8px;color:red;">[이름은 필수 정보입니다.]</b>');
				chk4 = false;
			}		         
			//이름값 유효성검사
			else if(!getName.test($("#user_name").val())){
			    $('#user_name').css("background-color", "pink");
				$('#nameChk').html('<b style="font-size:8px;color:red;">[두 글자 이상, 열 글자 이하의 한글로 작성해주세요.]</b>');
				chk4 = false;
			} else {
				$('#user_name').css("background-color", "#CCE1FF");
				$('#nameChk').html('');
				chk4 = true;
			}
			
		});
		
		$('#user_email').on('keyup', function() {
			//이메일값 공백 확인
			if($("#user_email").val() == ""){
			    $('#user_email').css("background-color", "pink");
				$('#emailChk').html('<b style="font-size:8px;color:red;">[이메일은 필수 정보입니다.]</b>');
				chk5 = false;
			}		         
			//이메일값 유효성검사
			else if(!getMail.test($("#user_email").val())){
			    $('#user_email').css("background-color", "pink");
				$('#emailChk').html('<b style="font-size:8px;color:red;">[이메일 형식으로 입력해주세요.]</b>');
				chk5 = false;
			} else {
				$('#user_email').css("background-color", "#CCE1FF");
				$('#emailChk').html('');
				chk5 = true;
			}
			
		});
		
		$('#user_dept').on('keyup', function() {
			//학과값 공백 확인
			if($("#user_dept").val() == ""){
			    $('#user_dept').css("background-color", "pink");
				$('#deptChk').html('<b style="font-size:8px;color:red;">[학과는 필수 정보입니다.]</b>');
				chk6 = false;
			}else {
				$('#user_dept').css("background-color", "#CCE1FF");
				$('#deptChk').html('');
				chk6 = true;
			}
			
		});

	});

	</script>
</body>
</html>

