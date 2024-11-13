<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<head>
<script>

function setCookie(cookie_name, value, days) {
	var exdate = new Date();
	exdate.setDate(exdate.getDate() + days);
	var cookie_value = escape(value) + ((days == null) ? '' : '; expires=' + exdate.toUTCString());
	document.cookie = cookie_name + '=' + cookie_value;
	}

function close_pop_allday(flag, popup_no) {
	_this = $(this);
	var popupString = "popup"+popup_no;
	if ( flag == true ) {
		setCookie(popupString,"end",1);
		$("#popup_"+popup_no).hide();
		}
	else{                 
		$("#popup_"+popup_no).hide();
	}
}    
</script>
<script type="text/javascript">
var popupArray = new Array();
<c:forEach items="${popupArticle}" var="item">
	popupArray.push({
		pop_no : "${item.pop_no}",
		pop_title : "${item.pop_title}",
		pop_img_path  : "${item.pop_img_path}",
		pop_url : "${item.pop_url}",
		pop_loc_top : "${item.pop_loc_top}",
		pop_loc_left : "${item.pop_loc_left}"}	
	);
</c:forEach>

function getCookies() {
	cookiedata = document.cookie;
	for(var i = 0; i<popupArray.length; i++){
		var popup_no = popupArray[i].pop_no;
		var popupid = "popup_"+popup_no;
		var strpop = "popup"+popup_no+"=end";
	    if (cookiedata.indexOf(strpop)<0 ){
			$("#popup_"+popup_no).show();
			document.getElementById(popupid).style.top = popupArray[i].pop_loc_top;
			document.getElementById(popupid).style.left = popupArray[i].pop_loc_left;
		}else{
			$("#popup_"+popup_no).hide();
		}
	}
}
</script>
</head>

<body onload="getCookies()">
<c:forEach var="popup" items="${popupArticle}" varStatus="status">
	<div class ="popup" id ="popup_${popup.pop_no}"  style="display:none; z-index:10000; position:fixed; background:#AAAAAA;">
		<c:choose>
			<c:when test="${not empty popup.pop_url}">
				<div style="width:400px; height:auto;"><img src="${popup.pop_img_path}" style="width:400px; cursor:pointer" onclick="window.open('<c:out value="${popup.pop_url}"/>','_blank')"></div>
			</c:when>
			<c:otherwise>
				<div style="width:400px; height:auto;"><img src="${popup.pop_img_path}" style="width:400px;"></div>
			</c:otherwise>
		</c:choose>
		<div style="cursor:pointer;background-color:#3d485f;text-align: center;">
		<span style="color:#FFFFFF;" class="pop_bt_nomore"  onclick="close_pop_allday(true, ${popup.pop_no});">[오늘 더이상 열지 않기]</span>
		<span style="float:right; color:#FFFFFF;" class="close_popup" onclick="close_pop_allday(false, ${popup.pop_no});">닫기</span>
	</div>
	</div>
</c:forEach>

</body>
</html>

