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
					<li><a href="${pageContext.request.contextPath}/${authUser.id}/admin/basic">기본설정</a></li>
					<li class="selected"><a href="${pageContext.request.contextPath}/${authUser.id}/admin/category">카테고리</a></li>
					<li><a href="${pageContext.request.contextPath}/${authUser.id}/admin/write">글작성</a></li>
				</ul>
				<table class="admin-cat">
					<thead>
						<tr>
							<th>번호</th>
							<th>카테고리명</th>
							<th>포스트 수</th>
							<th>설명</th>
							<th>삭제</th>
						</tr>
					</thead>
					<tbody id=cateList>
					</tbody>
				</table>
				<h4 class="n-c">새로운 카테고리 추가</h4>
				<table id="admin-cat-add">
					<tr>
						<td class="t">카테고리명</td>
						<td><input type="text" name="name" value="" id="cateName"></td>
					</tr>
					<tr>
						<td class="t">설명</td>
						<td><input type="text" name="desc" id="description"></td>
					</tr>
					<tr>
						<td class="s">&nbsp;</td>
						<td><input id="btnAddCate" type="submit" value="카테고리 추가"></td>
					</tr>
				</table>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/footer-blog.jsp"></c:import>
		<!-- footer -->
	</div>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		$.ajax({

			url : "${pageContext.request.contextPath }/${authUser.id}/admin/categoryList",
			type : "post",
			/* contentType : "application/json", *//* JSON 형태로 보낼 때 */
			/* data : {email : email}, */

			dataType : "json",
			success : function(categoryList) {
				/*성공시 처리해야될 코드 작성*/
				console.log(categoryList);

				for (var i = 0; i < categoryList.length; i++) {
					console.log("리스트 출력");
					render(categoryList[i], "down");
				}

			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	});
	
	$("#btnAddCate").on("click", function(){
		console.log("카테고리 추가");
		
		var cateName = $("#cateName").val();
		var description = $("#description").val();
		
		$.ajax({

			url : "${pageContext.request.contextPath }/${authUser.id}/admin/categoryAdd",
			type : "post",
			contentType : "application/json",
			data : JSON.stringify({cateName : cateName,
								   description : description}),

			dataType : "json",
			success : function(categoryVo) {
				/*성공시 처리해야될 코드 작성*/
				console.log(categoryVo);

				render(categoryVo, "up");
				
				$("#cateName").val("");
				$("#description").val("");
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	});
	
	$("#cateList").on("click", ".btn_cateDel", function(){
		console.log("삭제 버튼 클릭");
		
		var $this = $(this);
		var cateNo = $this.data("cateno");
		var postCnt = $this.data("postcnt");
		var id = "#cate" + cateNo;
		console.log("cateNo: " + cateNo + " / postCnt: " + postCnt);
		
		if(postCnt == 0){
			$.ajax({
	
				url : "${pageContext.request.contextPath }/${authUser.id}/admin/categoryDel",
				type : "post",
				contentType : "application/json",
				data : JSON.stringify({cateNo : cateNo}),
	
				dataType : "json",
				success : function(result) {
					/*성공시 처리해야될 코드 작성*/
					console.log(result);
	
					if(result){
						$(id).remove();
					}
				},
				error : function(XHR, status, error) {
					console.error(status + " : " + error);
				}
			});
		} else {
			alert("삭제할 수 없습니다.");
		}
	});
	
	function render(categoryVo, updown) {
		console.log("render 실행");
		var str = "";
		str += "<tr id='cate" + categoryVo.cateNo + "'>";
		str += "	<td>" + categoryVo.cateNo + "</td>";
		str += "	<td>" + categoryVo.cateName + "</td>";
		str += "	<td>" + categoryVo.postCnt + "</td>";
		str += "	<td>" + categoryVo.description + "</td>";
		str += "	<td><img class='btn_cateDel' style='cursor:pointer' src='../../assets/images/delete.jpg' data-cateno='" + categoryVo.cateNo + "' data-postcnt='" + categoryVo.postCnt + "'></td>";
		str += "</tr>";

		if (updown == "up") {
			$("#cateList").prepend(str);
		} else if (updown == "down") {
			$("#cateList").append(str);
		} else {
			console.log("updown 오류");
		}
	}
</script>
</html>