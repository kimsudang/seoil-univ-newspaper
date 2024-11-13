<%@page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@include file="page_header.jsp" %>
<%@page import="kr.seoil.vo.BoardVO" %>
<%@page import="java.io.File"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.commons.io.FileUtils"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%String sessionbStdID = (String)session.getAttribute("userStdID"); %>

<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" type="text/css" href="../css/editor.css?sf">
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
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
	   }else if (f.editCategory1.value == '' || f.editCategory1.value == 'none' || f.editCategory1.value == null) {
	        alert('카테고리를 선택해 주세요.');
	        f.editCategory.focus();
	        return false;
	   }else if (CKEDITOR.instances.editContent.getData() == '' || CKEDITOR.instances.editContent.getData() == null) {
	       alert('내용을 입력해 주세요.');
	       return false;
       }
		document.editForm.action = "/contentupload.do";
		document.editForm.submit(); 
	    return true; 
	}
	
	function forUpdateAction(){
		var f = document.editForm;
        //제목 입력x
		if (f.editTitle.value == '' || f.editTitle.value == null) {
	        alert('제목을 입력해 주세요.');
	        f.editTitle.focus();
	        return false;
	   }else if (f.editCategory1.value == '' || f.editCategory1.value == 'none' || f.editCategory1.value == null) {
	        alert('카테고리를 선택해 주세요.');
	        f.editCategory.focus();
	        return false;
	   }else if (CKEDITOR.instances.editContent.getData() == '' || CKEDITOR.instances.editContent.getData() == null) {
	       alert('내용을 입력해 주세요.');
	       return false;
       }
		document.editForm.action = "/contentupdateupload.do"; 
		document.editForm.submit(); 
	    return true; 
	}
	
	function setSelectBox(obj){
		var selectCategory = obj.value;
		$.ajax({
		    type: "POST",
		    url: "selectbox.do",//목록을 조회 할 url
		    data: {
				"category": selectCategory
			},
		    success: function(data){
		    	if(data != null && data != "[]"){
			    	$('#editCategory2').removeAttr("disabled");
					$('#editCategory2').children("option").remove();
			    	var list = $.parseJSON(data);
			    	for (var i=0; i<list.length; i++) {
			    		 var option = "<option value='" + list[i].cId + "'>" + list[i].cName + "</option>";        
						 $('#editCategory2').append(option);
			        }
					
				}else{
					$('#editCategory2').children("option").remove();
					$('#editCategory3').children("option").remove();
					var option = "<option value=''>중분류</option>"; 
					var option2 = "<option value=''>소분류</option>"; 
					$('#editCategory2').append(option);
					$('#editCategory2').attr('disabled', 'disabled');
					$('#editCategory3').append(option2);
					$('#editCategory3').attr('disabled', 'disabled');
				}

			},error :function(){
				alert("서버에 오류가 발생했습니다. 다시 진행해주세요.");
			}

		});

	}	
		
	function setSubSelectBox(obj){
		var selectCategory = obj.value;
		$.ajax({
		    type: "POST",
		    url: "selectbox.do",//목록을 조회 할 url
		    data: {
				"category": selectCategory
			},
		    success: function(data){
		    	if(data != null && data != "[]"){
			    	$('#editCategory3').removeAttr("disabled");
					$('#editCategory3').children("option").remove();
			    	var list = $.parseJSON(data);
			    	for (var i=0; i<list.length; i++) {
			    		 var option = "<option value='" + list[i].cId + "'>" + list[i].cName + "</option>";        
						 $('#editCategory3').append(option);
			        }
					
				}else{
					$('#editCategory3').children("option").remove();
					var option = "<option value=''>소분류</option>";        
					$('#editCategory3').append(option);
					$('#editCategory3').attr('disabled', 'disabled');
					
				}

			},error :function(){
				alert("서버에 오류가 발생했습니다. 다시 진행해주세요.");
			}

		});
	}
</script>
</head>
<body onload="noBack();" onpageShow="if(event.persisted) noBack();" onunload="">
<div class="page-container">
    <h2>기사 작성</h2>
    <form name="editForm" method="post">
    	<ul>
    		<li>
    			<label for="editCategory1" class="title">분류선택</label>
    			<select name="editCategory1" id="editCategory1" onchange="setSelectBox(this)" required>
				    <option value="">대분류</option>
				  	<c:forEach items="${headList}" var="headCategory" varStatus="status">
				  		<option value="${headCategory.cId}">${headCategory.cName}</option>
				  	</c:forEach>
				</select>

				<select name="editCategory2" id="editCategory2" onchange="setSubSelectBox(this)" disabled>
				    <option value="">중분류</option>
				</select>
				
				<select name="editCategory3" id="editCategory3" disabled>
				    <option value="">소분류</option>
				</select>
    		</li>
    		<li>
    			<label for="editTitle" class="title">제목</label>
    			<input type="text" id="editTitle" name="editTitle" class="input" placeholder="제목을 입력해 주세요." maxlength=50 value="${bTitle}" required>
    		</li>
    		<li>
    			<label for="editSubTitle" class="title">서브제목</label>
    			<input type="text" id="editSubTitle" name="editSubTitle" class="input" placeholder="서브제목을 입력해 주세요." maxlength=50 value="${bSubTitle}">
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
				    { name: 'styles', items: [ 'Styles', 'Format', 'Font'] }],
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
    	<input type="hidden" name="stdId" value="<%=sessionbStdID%>">
    	<input type="hidden" name="bAvailable" value="${bAvailable}">
    	<input type="hidden" name="bId" value="${bId}">
    	<input type="hidden" name="bReg_date" value="${bReg_date}">
    	
	   	<c:choose>
	   		<c:when test="${bId eq null}">
			    <button type="submit" class="btn btn-outline-secondary submitbtn" class="submitbtn" onclick="if(!confirm('글을 등록하시겠습니까?')){return false;}; return forAction();">기사 등록</button>
			</c:when>
			<c:otherwise>
				<input type="hidden" name="btnValue" value="update">
			    <button type="submit" class="btn btn-outline-secondary submitbtn" class="submitbtn" onclick="if(!confirm('글을 수정하시겠습니까?')){return false;}; return forUpdateAction();">기사 수정</button>
			</c:otherwise>
		</c:choose>
	</form>
</div>

</body>
</html>