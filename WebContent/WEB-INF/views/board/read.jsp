<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>mySite2</title>
<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite2/assets/css/board.css" rel="stylesheet" type="text/css">

</head>


<body>
	<div id="wrap">

		<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
		<!-- //header -->
		
		<div id="nav">
			<ul>
				<li><a href="/mysite2/guestBook?action=list">방명록</a></li>
				<li><a href="">갤러리</a></li>
				<li><a href="/mysite2/board?action=list">게시판</a></li>
				<li><a href="">입사지원서</a></li>
			</ul>
			<div class="clear"></div>
		</div>
		<!-- //nav -->

		<jsp:include page="/WEB-INF/views/include/asideBoard.jsp"></jsp:include>
		<!-- //aside -->

		<div id="content">

			<div id="content-head">
				<h3>게시판</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>게시판</li>
						<li class="last">일반게시판</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!-- //content-head -->

			<div id="board">
				<div id="read">
					<form action="#" method="get">
						<!-- 작성자 -->
						<div class="form-group">
							<span class="form-text">작성자</span>
							<span class="form-value">${requestScope.getBoard.name }</span>
						</div>
						
						<!-- 조회수 -->
						<div class="form-group">
							<span class="form-text">조회수</span>
							<span class="form-value">${requestScope.getBoard.hit }</span>
						</div>
						
						<!-- 작성일 -->
						<div class="form-group">
							<span class="form-text">작성일</span>
							<span class="form-value">${requestScope.getBoard.reg_date }</span>
						</div>
						
						<!-- 제목 -->
						<div class="form-group">
							<span class="form-text">제 목</span>
							<span class="form-value">${requestScope.getBoard.title }</span>
						</div>
					
						<!-- 내용 -->
						<div id="txt-content">
							<span class="form-value" >${requestScope.getBoard.content }</span>
						</div>
						<c:if test="${sessionScope.authUser.no eq getBoard.user_no}">
						<a id="btn_modify" href="/mysite2/board?action=modifyForm&no=${requestScope.getBoard.no }">수정</a>
						</c:if>
						<a id="btn_modify" href="/mysite2/board?action=list">목록</a>
						
					</form>
	                <!-- //form -->
				</div>
				<!-- //read -->
			</div>
			<!-- //board -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>

		<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
		<!-- //footer -->
	</div>
	<!-- //wrap -->

</body>

</html>
