<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="header">
		<h1>
			<a href="${pageContext.request.contextPath}/${blogVo.id}">${blogVo.blogTitle}</a>
		</h1>
		<ul>
			<c:choose>
				<c:when test="${empty sessionScope.authUser }">
					<!-- 로그인 전 메뉴 -->
					<li><a href="${pageContext.request.contextPath}/user/loginform">로그인</a></li>
				</c:when>
				<c:when test="${blogVo.id ne authUser.id}">
					<!-- 로그인 후 메뉴 -->
					<li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>
					<li><a href="${pageContext.request.contextPath}/${authUser.id}/admin/basic">내블로그 관리</a></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>
</body>
</html>