<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="../css/login.css?beer" rel="stylesheet" type="text/css">
<script>
	window.history.forward();
	function noBack(){window.history.forward();}
</script>
<title>로그인 - 서일학보</title>
</head>
<body onload="noBack();" onpageShow="if(event.persisted) noBack();" onunload="">
	
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
	
	<div class="container">
		<div class ="title">
			<a href="home.do" >
				<img src="../img/seoil_logo.png" alt="Logo" class="logo">
			</a>
		</div>
		<div class="loginform">
			<div class="jumbotron">
				<form method="post" action="userLogin.do">
					<p class="login">로그인</p>
					<div class="form-group">
						<p class="log">아이디<span id="idCheck"></span></p>
						<input type="text" class="form-control" placeholder="아이디" name="userStdID" id="signInStdId" maxlength="9" autofocus required>			
					</div>			
					<div class="form-group">
						<p class="log">비밀번호<span id="pwCheck"></span></p>
						<input type="password" class="form-control" placeholder="비밀번호" name="userPW" id="signInPw" required>			
					</div>
					<input type="submit" class="btn btn-primary form-control" value="로그인" id="signIn-btn">
					<button type="button" class="join-button" onclick="location.href='userJoin.do'">
					회원가입
					</button>
				</form>
			</div>
		</div>
		
		<div class="footer">
		</div>
	</div>
	
	<script type="text/javascript">
	//ID 입력값 검증.
	$(function(){
		var getIdCheck= RegExp(/^[0-9]{9}$/);
		var getPwCheck= RegExp(/([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])/);
		var chk1 = false, chk2 = false;
		
		$('#signInStdId').on('keyup', function() {
			if($("#signInStdId").val() == ""){
				$('#signInStdId').css("background-color", "pink");
				$('#idCheck').html('<b style="font-size:8px;color:red;">[아이디를 입력하세요.]</b>');
				chk1 = false;
			}else {
				$('#signInStdId').css("background-color", "#CCE1FF");
				$('#idChk').html('');
				chk1 = true;
			}
		});
		
		//패스워드 입력값 검증.
		$('#signInPw').on('keyup', function() {
			//비밀번호 공백 확인
			if($("#signInPw").val() == ""){
			    $('#signInPw').css("background-color", "pink");
				$('#pwCheck').html('<b style="font-size:8px;color:red;">[패스워드를 입력하세요.]</b>');
				chk2 = false;
			} else {
				$('#signInPw').css("background-color", "#CCE1FF");
				$('#pwCheck').html('');
				chk2 = true;
			}
			
		});
		
		$('#signIn-btn').click(function(e) {
			if(chk1 && chk2) {							
				$.ajax({
					type: "POST",
					url: "/check_login.jsp",
					data: {
						"userStdID": $('#signInStdId').val(),
						"userPW": $('#signInPw').val()
					},
					success: function(data) {
						if($.trim(data) == "NOT_PW" || $.trim(data) == "NOT_ID" ) {
							$('#signInStdId').css("background-color", "pink");
							$('#signInId').val("");
							$('#signInPw').css("background-color", "pink");
							$('#signInPw').val("");
							$('#signInId').focus();
							chk2 = false;
						} else if($.trim(data) == "LOGIN_OK") {
							location.href="/home.do";
						}
					}
				});			
			} else if( $("#signInPw").val() == "" || $("#signInStdId").val() == "" ) {
				alert('입력정보를 다시 확인하세요.');			
			}
		});
		
	});

	</script>
</body>
</html>