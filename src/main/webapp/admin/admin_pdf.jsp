<%@page import="java.sql.Connection"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<meta charset="UTF-8">
	<title>학보 등록</title>
	<link rel="stylesheet" type="text/css" href="../css/mypage.css?ar">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
	
	<script>
	function forAction(){
		var f = document.pdfForm;
		f.action = "/pdfRegister.do"; 
		f.submit(); 
	    return true; 
	}
	</script>
	
</head>
<body>
<%@include file="page_header.jsp" %>
	<div class="page-container">
		<div class="content">
			<h2>지면 학보 등록</h2>
			<section>
				<form name="pdfForm" method="post" enctype="multipart/form-data" accept-charset="UTF-8" action="pdfRegister.do">
					<ul>
						<li>
							<label for="pdf-Title" class="title">업로드 제목</label>
							<input type="text" size="40" class="paddingText" name="pdfTitle" id="pdf-Title" 
							placeholder="관리자가 보는 제목 설정입니다." maxlength="20" value="${articles.pdf_title}" required>
						</li>
						<li>
							<label for="pdf-Date" class="title">발행일</label>
							<input type="date" name="pdfDate" id="pdf-Date" max="3000-01-01" value="${articles.publish_date}" required>
							<p>학보의 발행일을 입력해주세요.</p>
						</li>
						<li>
							<label for="pdf-Number" class="title">발행 호수</label>
							<input type="number" id="pdf-Number" name="pdfNumber" min="0" pattern="[0-9]+" value="${articles.publish_no}"  required>
							<label for="pdf-Number" class="upload">호</label>
							<p>숫자만 기입해주세요.</p>
						</li>
						<li>
							<label for="pdf-File" class="title">파일첨부</label>
							<input type="file" id="pdf-File" name="pdfFile" accept=".pdf" value="${articles.pdf_file_path}" required/>
							<p>.pdf 형식의 파일을 업로드해주세요.</p>
						</li>
					</ul>
				    <button type="submit" class="btn btn-outline-secondary submitbtn" class="submitbtn" onclick="if(!confirm('pdf를 등록하시겠습니까?')){return false;}; return forAction();">등록</button>
				</form>
			</section>
		</div>
	</div>
</body>
</html>