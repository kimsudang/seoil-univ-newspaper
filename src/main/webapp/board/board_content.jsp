<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<% 
request.setCharacterEncoding("utf-8"); 
%>

<c:set var="bReg_date" value="${boardContent.getbReg_date()}"/>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />

<title>서일대학교 학보사</title>
<link rel="stylesheet" type="text/css" href="../css/index.css?fs">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://kit.fontawesome.com/ec08099fdd.js" crossorigin="anonymous"></script>
<script src="//cdn.ckeditor.com/4.19.0/full/ckeditor.js"></script>
	
<script type="text/javascript">
window.addEventListener('load', function(){
	var storyArray = new Array();
	<c:forEach items="${storyArticles}" var="item">
	storyArray.push({
		story_img_path : "${item.image_path}", 
		story_category: "${item.bCategory}", 
		story_idxno: "${item.idxno}"
	});
	</c:forEach>
	
	for(var i = 0; i<storyArray.length; i++){
		var adNum = "ad"+i;
		var adImg = "adImg" + i;
	    if (storyArray.length > 0){
			document.getElementById(adImg).src = storyArray[i].story_img_path;
			var link = "boardContent.do?category=" + storyArray[i].story_category + "&idxno=" + storyArray[i].story_idxno;
			document.getElementById(adNum).href = link;
		}
	}
});
</script>
<script>
$(document).ready(function() {
  $('.prev').click(function() {
    $('.story-item').stop().animate({
      'margin-left': '-400px'
    }, function() {
      $('.story-item div:first-child').appendTo('.story-item');
      $('.story-item').css({
        'margin-left': '-210px'
      });
    });
  });

  $('.next').click(function() {
    $('.story-item').stop().animate({
      'margin-left': '0px'
    }, function() {
      $('.story-item div:last-child').prependTo('.story-item');
      $('.story-item').css({
        'margin-left': '-210px'
      });
    });
  });
});
</script>

</head>
<body>
<div class="user-wrap">
	<div class="wrap">
		<div class="body-content">
			<div class="wrap-section">
			<section>
				<c:choose>
					<c:when test="${boardContent.getbCategory() eq '14'}">
						<header class="article-content-header">
								<h4 class="news-category">${boardContent.getcName()}</h4>
								<h3 class="news-title">${boardContent.getbTitle()}</h3>
						</header>
					</c:when>
					<c:otherwise>
						<header class="article-content-header">
							<h4 class="news-category">${boardContent.getcName()}</h4>
							<h3 class="news-title">${boardContent.getbTitle()}</h3>
							<h4 class="news-subTitle">${boardContent.getbSubTitle()}</h4>
							<div class="info-group">
								<article class="article-item">
									<ul class="article-information">
										<li>
											<i class="fa-solid fa-user">
												<span class="show-reporter">기자명 ${boardContent.getUserName()}기자</span>
											</i>			
										</li>
										<li>
											<i class="fa-solid fa-envelope">
												<span class="show-email">이메일 ${boardContent.getUserEmail()}</span>
											</i>			
										</li>
										<li>
											<i class="fa-solid fa-clock">
												<span class="show-regdate">작성일 <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${boardContent.getbReg_date()}"/></span>
											</i>			
										</li>
										<c:if test="${not empty boardContent.getbMod_date()}">
										<li>
											<i class="fa-solid fa-clock">
												<span class="show-moddate">수정일 <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${boardContent.getbMod_date()}"/></span>
											</i>			
										</li>
										</c:if>
									</ul>
								</article>
							</div>
						</header>
					</c:otherwise>
				</c:choose>
				<div class="inner-content">
					<div class="wrap-news">	
						<div class="wrap-news-article">
							<section id="articleViewContent" class="article-view-content">
								<article class="article-body">
									<div class="article-content-body">
										<article id="article-view-content-body">
											${boardContent.getbContent()}
										</article>
									</div>
								</article>
							</section>
							<c:if test="${boardContent.getbCategory() eq '15'}">
								<section class="another-article">
									<div id="demo" class="carousel slide" data-bs-ride="carousel">
										<div class="box-wrap">
											<div class="story-box">
												<div class="story-item" id="story-item">
													<c:forEach items="${storyArticles}" var="i" varStatus="status">
													<div class="story-content">
														<a id="ad${status.index}">
															<img alt="Image${status.index}" class="d-block story-img" id="adImg${status.index}">
														</a>
													</div>
													</c:forEach>
												</div>
											</div>
										</div>
										
										<div class="prev">
										    <span class="carousel-control-prev-icon c"></span>
										</div>
										<div class="next">
										    <span class="carousel-control-next-icon c"></span>	
										</div>
									</div>
								</section>
							</c:if>
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
</body>
</html>