<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.12.4.js"></script>
<title>JBlog</title>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header-blog.jsp"></c:import>
		<!-- header -->
		<div id="wrapper">
			<div id="content" class="full-screen">
				<ul class="admin-menu">
					<li class="selected"><a href="${pageContext.request.contextPath}/${authUser.id}/admin/basic">기본설정</a></li>
					<li><a href="${pageContext.request.contextPath}/${authUser.id}/admin/category">카테고리</a></li>
					<li><a href="${pageContext.request.contextPath}/${authUser.id}/admin/write">글작성</a></li>
				</ul>
				<form action="${pageContext.request.contextPath}/${authUser.id}/admin/basic/modify" method="post" enctype="multipart/form-data">
					<table class="admin-config">
						<tr>
							<td class="t">블로그 제목</td>
							<td><input type="text" size="40" name="blogTitle" value="${blogVo.blogTitle}"></td>
						</tr>
						<tr>
							<td class="t">로고이미지</td>
							<td><c:choose>
									<c:when test="${empty blogVo.logoFile}">
										<!-- 기본 로고 -->
										<img src="${pageContext.request.contextPath }/assets/images/spring-logo.jpg" id="img">
									</c:when>
									<c:otherwise>
										<!-- 등록한 경우 자신의 로고 -->
										<img src="${pageContext.request.contextPath }/upload/${blogVo.logoFile}" id="img">
									</c:otherwise>
								</c:choose></td>
						</tr>
						<tr>
							<td class="t">&nbsp;</td>
							<td><input type="file" name="file" id="imgInp"></td>
						</tr>
						<tr>
							<td class="t">&nbsp;</td>
							<td class="s"><input type="submit" value="기본설정 변경"></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/footer-blog.jsp"></c:import>
		<!-- footer -->
	</div>
</body>
<script type="text/javascript">
	function readURL(input) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();
			reader.onload = function(e) {
				$('#img').attr('src', e.target.result);
			}
			reader.readAsDataURL(input.files[0]);
		}
	}

	$("#imgInp").change(function() {
		readURL(this);
	});
</script>
</html>