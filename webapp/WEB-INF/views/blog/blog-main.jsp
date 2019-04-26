<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	pageContext.setAttribute("newLine", "\n");
%>
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
			<div id="content">
				<div class="blog-content">
					<c:choose>
						<c:when test="${empty postVo}">
							<!-- 등록된 글이 없을때 -->
							<h4>등록된 글이 없습니다.</h4>
							<p>
							<p>
						</c:when>
						<c:otherwise>
							<!-- 등록된 글이 있을때 -->
							<h4>${postVo.postTitle}</h4>
							<br>
							<div>${fn:replace(postVo.postContent, newLine, "<br>")}</div>
							<br>
							<c:if test="${not empty authUser}">
								<form>
									<input type="hidden" name="userNo" value="${authUser.userNo}"> <input type="hidden" name="postNo" value="${postVo.postNo}">
									<table style="width: 700px; height: 30px; text-align: center" border="1">
										<tr>
											<td style="width: 70px">${authUser.userName}</td>
											<td><input type="text" name="comments" value="" style="width: 540px; height: 23px"></td>
											<td><input id="btnAddComment" type="button" value="저장" style="width: 70px; height: 23px"></td>
										</tr>
									</table>
								</form>
							</c:if>
							<div id="commentList"></div>
						</c:otherwise>
					</c:choose>
				</div>
				<ul class="blog-list">
					<c:forEach items="${postList}" var="postVo">
						<li><a href="${pageContext.request.contextPath }/${blogVo.id}?cateNo=${postVo.cateNo}&postNo=${postVo.postNo}&crtPage=${crtPage}">${postVo.postTitle}</a><span>${postVo.regDate}</span></li>
					</c:forEach>
				</ul>
				<div class="pager">
					<ul>
						<c:if test="${map.prev eq true }">
							<li><a href="${pageContext.request.contextPath}/${blogVo.id}?cateNo=${postVo.cateNo}&crtPage=${map.startPageBtnNo-1}">◀</a></li>
						</c:if>
						<c:forEach begin="${map.startPageBtnNo }" end="${map.endPageBtnNo }" step="1" var="page">
							<c:choose>
								<c:when test="${param.crtPage eq page }">
									<li class="selected"><a href="${pageContext.request.contextPath}/${blogVo.id}?cateNo=${postVo.cateNo}&crtPage=${page}">${page}</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="${pageContext.request.contextPath}/${blogVo.id}?cateNo=${postVo.cateNo}&crtPage=${page}">${page}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${map.next eq true }">
							<li><a href="${pageContext.request.contextPath}/${blogVo.id}?cateNo=${postVo.cateNo}&crtPage=${map.endPageBtnNo+1}">▶</a></li>
						</c:if>
					</ul>
				</div>
			</div>
		</div>
		<div id="extra">
			<div class="blog-logo">
				<c:choose>
					<c:when test="${empty blogVo.logoFile}">
						<!-- 기본 로고 -->
						<img src="${pageContext.request.contextPath }/assets/images/spring-logo.jpg">
					</c:when>
					<c:otherwise>
						<!-- 등록한 경우 자신의 로고 -->
						<img src="${pageContext.request.contextPath }/upload/${blogVo.logoFile}">
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div id="navigation">
			<h2>카테고리</h2>
			<ul>
				<c:forEach items="${categoryList}" var="categoryVo">
					<li><a href="${pageContext.request.contextPath }/${blogVo.id}?cateNo=${categoryVo.cateNo}">${categoryVo.cateName}</a></li>
				</c:forEach>
			</ul>
		</div>
		<c:import url="/WEB-INF/views/includes/footer-blog.jsp"></c:import>
		<!-- footer -->
	</div>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		var postNo = ${postVo.postNo};
		console.log(postNo);

		$.ajax({

			url : "${pageContext.request.contextPath }/${blogVo.id}/getComment",
			type : "post",
			contentType : "application/json",
			data : JSON.stringify({
				postNo : postNo
			}),

			dataType : "json",
			success : function(response) {
				/*성공시 처리해야될 코드 작성*/
				console.log(response);

				for (var i = 0; i < response.data.length; i++) {
					console.log("댓글 출력");
					render(response.data[i], "up");
				}

			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	});

	$("#btnAddComment").on("click",function() {
		console.log("댓글 쓰기");

		var userNo = $("[name=userNo]").val();
		var postNo = $("[name=postNo]").val();
		var cmtContent = $("[name=comments]").val();
		console.log(userNo);
		console.log(postNo);
		console.log(cmtContent);

		$.ajax({

			url : "${pageContext.request.contextPath }/${blogVo.id}/addComment",
			type : "post",
			contentType : "application/json",
			data : JSON.stringify({
				userNo : userNo,
				postNo : postNo,
				cmtContent : cmtContent
			}),

			dataType : "json",
			success : function(response) {
				/*성공시 처리해야될 코드 작성*/
				console.log(response);

				render(response.data, "up");
				$("[name=comments]").val("");
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	});

	$("#commentList").on("click","#cmtDel",function() {
		var $this = $(this);
		var cmtNo = $this.data("no");

		console.log(cmtNo);

		$.ajax({

			url : "${pageContext.request.contextPath }/${blogVo.id}/delComment",
			type : "post",
			contentType : "application/json",
			data : JSON.stringify({
				cmtNo : cmtNo
			}),

			dataType : "json",
			success : function(response) {
				/*성공시 처리해야될 코드 작성*/
				console.log(response);

				if (response.result === "success") {
					var delComm = "#comment"+ response.data;
					$(delComm).remove();
				} else {
					console.log("댓글 삭제 실패");
				}
			},
			error : function(XHR, status, error) {
			console.error(status + " : " + error);
			}
		});
	});

	function render(commentVo, updown) {
		console.log("render 실행");

		var str = "";
		str += "<table style='width:700px; height:25px;' id='comment" + commentVo.cmtNo + "'>";
		str += "	<tr>";
		str += "		<td style='width:73px; text-align:center'>"
				+ commentVo.userName + "</td>";
		str += "		<td style='width:530px; height:23px; padding-left:7px'>"
				+ commentVo.cmtContent + "</td>";
		str += "		<td>" + commentVo.regDate + "</td>";
		if ("${authUser.userNo}" == commentVo.userNo) {
			str += "		<td><img id='cmtDel' data-no=" + commentVo.cmtNo + " style='cursor:pointer' src='${pageContext.request.contextPath }/assets/images/delete.jpg'></td>";
		} else {
			str += "<td></td>";
		}
		str += "	</tr>";
		str += "</table>";

		if (updown == "up") {
			$("#commentList").prepend(str);
		} else if (updown == "down") {
			$("#commentList").append(str);
		} else {
			console.log("updown 오류");
		}
	}
</script>
</html>