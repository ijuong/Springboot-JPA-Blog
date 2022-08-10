<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<div class="container">

	<form action="/auth/loginProc" method="POST">
		<div class="form-group">
			<label for="email">Username</label> 
			<input name="username" type="text" class="form-control" placeholder="Enter username" id="username">
		</div>
		
		<div class="form-group">
			<label for="pwd">Password:</label> 
			<input name="password" type="password" class="form-control" placeholder="Enter password" id="password">
		</div>
		<!--  
		<div class="form-group form-check">
			<label class="form-check-label"> 
				<input name="remember" class="form-check-input" type="checkbox"> Remember me
			</label>
		</div>
		-->
		<button id="btn-login" class="btn btn-primary">로그인</button>
		<a href="https://kauth.kakao.com/oauth/authorize?client_id=cefb5d15a9f6b2fbe6b2b40a5a1a78b6&redirect_uri=http://localhost:8000/auth/kakao/callback&response_type=code"><img height="38px" src="/image/kakao_login_button.png"></img></a>
	</form>
</div>

<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>

