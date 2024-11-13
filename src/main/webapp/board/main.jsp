<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<%@ include file="popup.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />

<title>서일대학교 학보사</title>
<link rel="stylesheet" type="text/css" href="../css/index.css?fg">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://kit.fontawesome.com/ec08099fdd.js" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>

<script>
window.addEventListener('load', function(){
	var advertiseArray = new Array();
	<c:forEach items="${advertiseArticles}" var="item">
	advertiseArray.push({
		advertise_img_path : "${item.image_path}", 
		advertise_category: "${item.bCategory}", 
		advertise_idxno: "${item.idxno}"
	});
	</c:forEach>
	
	for(var i = 0; i<advertiseArray.length; i++){
		var adNum = "ad"+i;
		var adImg = "adImg" + i;
	    if (advertiseArray.length > 0){
			document.getElementById(adImg).src = advertiseArray[i].advertise_img_path;
			var link = "boardContent.do?category=" + advertiseArray[i].advertise_category + "&idxno=" + advertiseArray[i].advertise_idxno;
			document.getElementById(adNum).href = link;

		}
	}
});
</script>
</head>
<body>
<div class="user-wrap">
	<div class="wrap">
		<!-- 내용 -->
		<div class="body-content">
			<div class="wrap-section">
				<section>
					<div class="inner-content">
						<div class="wrap-news">
							<div class="main">
								<div class="pic">
									<c:if test="${not empty mainArticle.image_path}" var="imageShow">
										<a href="boardContent.do?category=${mainArticle.bCategory}&idxno=${mainArticle.idxno}">
										<img src="${mainArticle.image_path}" alt="뉴스 이미지"></a>
									</c:if>
								</div>
								<div class="newstext-main">
									<h2>
										<a href="boardContent.do?category=${mainArticle.bCategory}&idxno=${mainArticle.idxno}">
											${mainArticle.bTitle}
										</a>
									</h2>
									<p><a href="boardContent.do?category=${mainArticle.bCategory}&idxno=${mainArticle.idxno}">
										${mainArticle.text_only}
									</a>
									</p>
								</div>
							</div>
							<div class="newscategory">
								<div class="navbar">
									<p>보도</p>
									<a href="news.do?page=1&category=1"><i class="fa-solid fa-plus"></i></a>
								</div>
								<c:forEach var="firstArticles" items="${firstArticles}">
								<div class="sub">
									<div class="pic">
										<c:if test="${not empty firstArticles.image_path}" var="imageShow">
											<td>
												<div>
													<a href="boardContent.do?category=${firstArticles.bCategory}&idxno=${firstArticles.idxno}">
													<img src="${firstArticles.image_path}" style="width: 150px; height:100px;"></a>
												</div>
											</td>
										</c:if>
									</div>
									<div class="newstext">
										<h4><a href="boardContent.do?category=${firstArticles.bCategory}&idxno=${firstArticles.idxno}">
											${firstArticles.bTitle}
										</a></h4>
										<p><a href="boardContent.do?category=${firstArticles.bCategory}&idxno=${firstArticles.idxno}">
											${firstArticles.text_only}
										</a></p>
									</div>	
								</div>
								</c:forEach>
							</div>
							<div class="newscategory">
								<div class="navbar">
									<p>서일</p>
									<a href="news.do?page=1&category=2"><i class="fa-solid fa-plus"></i></a>
								</div>
								<c:forEach var="secondArticles" items="${secondArticles}">
								<div class="sub">			
									<div class="pic">
										<c:if test="${not empty secondArticles.image_path }" var="imageShow">
											<td>
												<div>
													<a href="boardContent.do?category=${secondArticles.bCategory}&idxno=${secondArticles.idxno}">
													<img src="${secondArticles.image_path}" style="width: 150px; height:100px;"></a>
												</div>
											</td>
										</c:if>
									</div>
									<div class="newstext">
										<h4><a href="boardContent.do?category=${secondArticles.bCategory}&idxno=${secondArticles.idxno}">
											${secondArticles.bTitle}
										</a></h4>
										<p><a href="boardContent.do?category=${secondArticles.bCategory}&idxno=${secondArticles.idxno}">
											${secondArticles.text_only}
										</a></p>
									</div>							
								</div>
								</c:forEach>
							</div>
							<div class="newscategory-bottom">
								<div class="div-inner">
									<div class="navbar">
										<p>광고</p>
										<a href="news.do?page=1&category=18"><i class="fa-solid fa-plus"></i></a>
									</div>
									
									<div id="demo" class="carousel slide" data-bs-ride="carousel">
										<div class="carousel-indicators">
											<button type="button" data-bs-target="#demo" data-bs-slide-to="0" class="active"></button>
											<button type="button" data-bs-target="#demo" data-bs-slide-to="1"></button>
											<button type="button" data-bs-target="#demo" data-bs-slide-to="2"></button>
										</div>
									
										<div class="carousel-inner">
											<div class="carousel-item active">
												<a id="ad0">
													<img src="" alt="Los Angeles" class="d-block" id="adImg0">
												</a>
											</div>
										    <div class="carousel-item">
										    	<a id="ad1">
										        	<img src="img/img(2).jpg" alt="Chicago" class="d-block" id="adImg1">
										        </a>
										    </div>
										    <div class="carousel-item">
										    	<a id="ad2">
										        	<img src="img/img(3).jpg" alt="New York" class="d-block" id="adImg2">
										        </a>
										    </div>
										</div>
										
										<button class="carousel-control-prev" type="button" data-bs-target="#demo" data-bs-slide="prev">
										    <span class="carousel-control-prev-icon"></span>
										</button>
										<button class="carousel-control-next" type="button" data-bs-target="#demo" data-bs-slide="next">
										    <span class="carousel-control-next-icon"></span>
										</button>
									</div>
							    </div>
							    <div class="div-inner" id="cartoon">
								    <div class="navbar">
										<p>만평</p>
										<a href="news.do?page=1&category=13"><i class="fa-solid fa-plus"></i></a>
									</div>
									
									<div class="satirical-img">
										<c:forEach var="satiricalArticle" items="${satiricalArticle}">
											<a href="boardContent.do?category=${satiricalArticle.bCategory}&idxno=${satiricalArticle.idxno}">
											<img class="satirical-img" src="${satiricalArticle.image_path}" alt="만평 이미지"></a>
										</c:forEach>
									</div>
								</div>
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
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>