<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="page_header.jsp" %>
<%@page import="java.io.File"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.commons.io.FileUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" type="text/css" href="../css/editor.css?ss">
<meta name="viewport" content="width=device-width, initial-scale=1.0, viewport-fit=cover">
<title>Home</title>
<script>
	window.history.forward();
	function noBack(){window.history.forward();}
	
	function forAction(){
		var f = document.editForm;
        //제목 입력x
		if (f.editTitle.value == '' || f.editTitle.value == null) {
	        alert('제목을 입력해 주세요.');
	        f.editTitle.focus();
	        return false;
	   }else if (CKEDITOR.instances.editContent.getData() == '' || CKEDITOR.instances.editContent.getData() == null) {
	       alert('내용을 입력해 주세요.');
	       return false;
       }
		document.editForm.action = "/advertiseupload.do"; 
		document.editForm.submit(); 
	    return true; 
	}
	
	function forUpdateAction(){
		var f = document.editForm;
		if (f.editTitle.value == '' || f.editTitle.value == null) {
	        alert('제목을 입력해 주세요.');
	        f.editTitle.focus();
	        return false;
	   }else if (CKEDITOR.instances.editContent.getData() == '' || CKEDITOR.instances.editContent.getData() == null) {
	       alert('내용을 입력해 주세요.');
	       return false;
       }
		document.editForm.action = "/advertiseupdateupload.do"; 
		document.editForm.submit(); 
	    return true; 
	}

</script>
</head>
<body onload="noBack();" onpageShow="if(event.persisted) noBack();" onunload="">
<div class="page-container">
    <h2>광고 작성</h2>
    <form name="editForm" method="post">
    	<ul>
    		<li>
    			<label for="editTitle" class="title">제목</label>
    			<input type="text" id="editTitle" name="editTitle" class="input" placeholder="제목을 입력해 주세요." value="${bTitle}" required>
    		</li>
    		<li>
    			<label class="title">내용</label>
    			<textarea name="editContent" id="editContent" style="display:none" required><c:out value="${bContent}" /></textarea>
			    <script src="//cdn.ckeditor.com/4.19.0/full/ckeditor.js"></script>
				<script>
					CKEDITOR.plugins.addExternal( 'image2', '/ckeditor/image2/', 'plugin.js' );
					CKEDITOR.replace("editContent",{
				    extraPlugins: 'image2',
				    toolbar : [
				    { name: 'clipboard', items: [ 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Undo', 'Redo' ] },             
				     '/', 
				    { name: 'basicstyles', items: [ 'Bold', 'Italic', 'Underline', 'Strike', 'Subscript', 'Superscript', '-', 'RemoveFormat' ] },
				    { name: 'insert', items: [ 'Image', 'Flash', 'HorizontalRule', 'SpecialChar' ] },  
				    { name: 'paragraph', items: ['Outdent', 'Indent', '-', 'Blockquote', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock', '-', 'BidiLtr', 'BidiRtl' ] }, 
				
				    '/', 
				    { name: 'styles', items: [ 'Styles', 'Format', 'Font' ] }],
				    docType : '<!DOCTYPE html>',
				    font_defaultLabel : '나눔고딕',
				    font_names : '나눔고딕/NanumGothic;돋움/Dotum;맑은 고딕/Malgun Gothic;굴림/Gulim;바탕/Batang;궁서/Gungsuh;',
				    language : "ko", 
				    resize_enabled : false, 
				    enterMode : CKEDITOR.ENTER_BR,
				    shiftEnterMode : CKEDITOR.ENTER_P,
				    startupFocus : true, 
				    uiColor : '#CCE5FF', 
				    toolbarCanCollapse : false, 
				    menu_subMenuDelay : 0,
				    filebrowserUploadMethod:'form',
				    filebrowserUploadUrl : '/imgupload.do',
				    removeDialogTabs: 'image:advanced;link:advanced',
				    resize_enabled : false,         
				    width : 900,
				    height : 450
				   });
				</script>
    		</li>
    	</ul>
    	<input type="hidden" name="stdId" value="${userStdID}">
    	<input type="hidden" name="bAvailable" value="${bAvailable}">
    	<input type="hidden" name="bId" value="${bId}">
    	<input type="hidden" name="bReg_date" value="${bReg_date}">
    	
    	<c:choose>
    		<c:when test="${bId eq null}">
	    		<button type="submit" class="btn btn-outline-secondary submitbtn" class="submitbtn" onclick="if(!confirm('글을 등록하시겠습니까?')){return false;}; return forAction();">광고 등록</button>
	   	 	</c:when>
	   	 	<c:otherwise>
	   	 		<input type="hidden" name="btnValue" value="update">
	    		<button type="submit" class="btn btn-outline-secondary submitbtn" class="submitbtn" onclick="if(!confirm('글을 수정하시겠습니까?')){return false;}; return forUpdateAction();">광고 수정</button>
	    	</c:otherwise>
	    </c:choose>
    </form>
</div>

</body>
</html>