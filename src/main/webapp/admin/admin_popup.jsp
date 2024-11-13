<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="kr.seoil.dao.TempDAO" %>
<%@page import="kr.seoil.vo.TempVO" %>
<%@page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="UTF-8">
<title>팝업 관리</title>
<link rel="stylesheet" type="text/css" href="../css/mypage.css?as">
<link rel="stylesheet" type="text/css" href="../css/popup.css?fsd">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">

<script type="text/javascript">	
let dateElement = document.querySelector('input[type="datetime-local"]');
let date = new Date(new Date().getTime() - new Date().getTimezoneOffset() * 60000).toISOString().slice(0, -5);
dateElement.value = date;
dateElement.setAttribute("min", date );
		
function setThumbnail(event) {
    var reader = new FileReader();

    reader.onload = function(event) {
      var img = document.getElementById("image_preview");
      img.setAttribute("src", event.target.result);
  
    };

    reader.readAsDataURL(event.target.files[0]);
  }
	function urlCheck(){
		var url = document.querySelector("input[name='popupUrl']");
		var regex = /(http(s)?:\/\/)([a-z0-9\w]+\.*)+[a-z0-9]{2,4}/gi;
		if(!regex.test(url.value)){
			alert('url은 http://나 https://로 시작하도록 입력해주세요.');
			url.value = "";
			return false;
		}
		return true;
	}

	function checkDate() {
		const now = new Date();
		var dateString = document.getElementById('popup-StartDate').value;
		var dateString2 = document.getElementById('popup-EndDate').value;
		var DateStart = new Date(dateString);
		var DateEnd = new Date(dateString2);
		if (DateEnd < DateStart) {
		    alert("종료시점이 시작시점보다 빠르게 지정될 수 없습니다.");
		    now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
		    document.getElementById('popup-EndDate').value = now.toISOString().slice(0, 16);
		    return false;
		}else if (DateEnd == DateStart) {
		    alert("종료일이 시작일과 같게 지정될 수 없습니다.");
		    now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
		    document.getElementById('popup-EndDate').value = now.toISOString().slice(0, 16);
		    return false;
		}else if(DateStart < Date.now()){
			 alert("시작시점이 현재시각보다 이전으로 지정될 수 없습니다.");
			 now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
			 document.getElementById('popup-StartDate').value = now.toISOString().slice(0, 16);
			 return false;
		}
		  return true;
	}

	function forAction(){
		var f = document.popForm;
		f.action = "/popupmanage.do"; 
		f.submit(); 
	    return true; 
	}
	
	function forUpdateAction(){
		var f = document.popForm;

		f.action = "/popupupdateupload.do"; 
		f.submit(); 
	    return true; 
	}

</script>
	
</head>
<body>
	<%@include file="page_header.jsp" %>
	
	<div class="page-container">
		<h2>팝업 등록</h2>
		<div class="inner-content">
			<div class="panel-body">
				<form method="POST" enctype="multipart/form-data" accept-charset="UTF-8" name="popForm">
					<ul>
						<li>
							<label for="popup-Title" class="title">관리용 제목</label>
							<input type="text" size="50" name="popupTitle" id="popup-Title" class="paddingText" placeholder="관리자가 팝업을 구분하기 위한 제목 설정입니다." maxlength="20" value="${articles.pop_title}" required>
						</li>
						<li>
							<label for="popup-StartDate" class="title">기간</label>
							<input type="datetime-local" name="popupStartDate" id="popup-StartDate" max="3000-01-01" onchange="checkDate()" value="${articles.start_date}" required>
    						<span class="validity"></span>부터
    						
    						
							<input type="datetime-local" name="popupEndDate" id="popup-EndDate" max="3000-01-01" onchange="checkDate()" value="${articles.end_date}" required>
    						<span class="validity"></span>까지
						</li>
						<li>
							<label for="popup-Img" class="title">이미지</label>
							<img id="image_preview" width="400px" value="${articles.pop_img_path}">
							<input type="file" id="popup-Img" name="popupImg" accept=".jpg, .png" onchange="setThumbnail(event);" value="${articles.pop_img_path}" required/>
							<p>이미지는 2MB이내의 것으로 업로드 해주세요. .jpg, .png 형식의 사진을 사용해주세요.</p>
	
						</li>
						<li>
							<label for="popup-Url" class="title">이미지 링크</label>
							<input type="text" size="60" name="popupUrl" id="popup-Url"  class="paddingText" onchange="urlCheck()" placeholder="http:// 또는 https://로 시작하는 형태" value="${articles.pop_url}" >
							<p>팝업을 눌렀을 때 이동할 페이지를 지정합니다. 따라서, 먼저 글을 등록하신 후 해당 글의 Url을 복사해 지정하시길 권장합니다.</p>
						</li>
						<li>
							<label for="popup-Place" class="title">팝업창 위치</label>
							페이지 상단에서 <input type="number" id="popup-Place"  name="popupTop" min="0" max="300" pattern="[0-9]+" value="${articles.pop_loc_top}" required>px
							페이지 좌측에서 <input type="number" id="popup-Place"  name="popupLeft" min="0" max="1000" pattern="[0-9]+" value="${articles.pop_loc_left}" required>px
							<p>페이지에서의 위치를 지정합니다. 이미지의 좌측 상단 모서리 기준입니다.</p>
							<p>상단에서 300, 좌측에서 1000까지 값 부여가 가능합니다.</p>
							<input type="hidden" name="pop_no" value="${articles.pop_no}">
							<input type="hidden" name="originPath" value="${articles.pop_img_path}">
							<input type="hidden" name="pReg_date" value="${articles.pReg_date}">
						</li>
					</ul>
					
					<c:choose>
						<c:when test="${state == '1'}">
				    		<button type="submit" class="btn btn-outline-secondary submitbtn" class="submitbtn" onclick="if(!confirm('팝업을 수정하시겠습니까?')){return false;}; return checkDate(); return urlCheck(); forUpdateAction();">팝업 수정</button>
				    	</c:when>
						<c:otherwise>
				    		<button type="submit" class="btn btn-outline-secondary submitbtn" class="submitbtn" onclick="if(!confirm('팝업을 등록하시겠습니까?')){return false;}; return checkDate(); return urlCheck(); forAction();">팝업 등록</button>
				    	</c:otherwise>
					</c:choose>
				</form>
			</div>
		</div>

	</div>
</body>
</html>