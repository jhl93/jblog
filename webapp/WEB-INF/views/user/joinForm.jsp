<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.12.4.js"></script>
<title>JBlog</title>
</head>
<body>
	<div class="center-content">
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<!-- header -->
		<form class="join-form" id="join-form" method="post" action="${pageContext.request.contextPath}/user/join">
			<label class="block-label" for="name">이름</label> 
			<input type="text" name="userName" value="" /> 
			<label class="block-label" for="id">아이디</label> 
			<input type="text" name="id" value="" id="id"/> 
			<input id="btn-checkid" type="button" value="id 중복체크">
			<input type="hidden" name="idCheck" value="false">
			<p id="checkid-msg" class="form-error">&nbsp;</p>
			<label class="block-label" for="password">패스워드</label> 
			<input type="password" name="password" value="" />
			<fieldset>
				<legend>약관동의</legend>
				<input id="agree-prov" type="checkbox" name="agreeProv" value="y"> 
				<label class="l-float">서비스 약관에 동의합니다.</label>
			</fieldset>
			<input id="btnSubmit" type="button" value="가입하기">
		</form>
	</div>
</body>

<script type="text/javascript">

	$("#btnSubmit").on("click", function(){
		var allCheck = false;
		
		if($("[name=userName]").val() == ""){
			alert("이름을 입력해 주세요");
		} else if($("[name=id]").val() == "") {
			alert("아이디를 입력해 주세요");
		} else if($("[name=idCheck]").val() == "false") {
			alert("아이디 중복 체크를 해주세요");
		} else if($("[name=password]").val() == "") {
			alert("비밀번호를 입력해 주세요");
		} else if($("input[name=agreeProv]").is(":checked") == false){
			alert("약관에 동의해 주세요");
		} else {
			allCheck = true;
		}
		
		if(allCheck == true){
			$("#join-form").submit();
			$("[name=idCheck]").val("false");
		}
	});

	$("#btn-checkid").on("click", function(){
		console.log("id중복체크 클릭");
		var id = $("#id").val();
		console.log(id);
		
		$.ajax({
			
			url : "${pageContext.request.contextPath }/user/idcheck",		
			type : "post",
			/* contentType : "application/json", */  /* JSON 형태로 보낼 때 */
			data : {id: id},

			dataType : "json",
			success : function(result){
				/*성공시 처리해야될 코드 작성*/
				console.log(result);
				if(result == true){
					$("#checkid-msg").text("사용할 수 있는 아이디 입니다.")
					$("[name=idCheck]").val("true");
				} else {
					$("#checkid-msg").text("사용할 수 없는 아이디 입니다.")
				}
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	});
</script>
</html>