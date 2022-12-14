<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<div class="container">

	<form>
		<input type="hidden" id="id" value="${principal.userInfo.id}" />
		<div class="form-group">
			<label for="username">Username</label> 
			<input type="text" value="${principal.userInfo.username }" class="form-control" placeholder="Enter username" id="username" readonly>
		</div>

		<c:if test="${empty principal.userInfo.oauth }">
			<div class="form-group">
				<label for="password">Password:</label> 
				<input type="password" class="form-control" placeholder="Enter password" id="password">
			</div>		
			<div class="form-group">
				<label for="email">Email</label> 
				<input type="email" value="${principal.userInfo.email }" class="form-control" placeholder="Enter email" id="email">
			</div>
		</c:if>		
	</form>
	
	<button id="btn-update" class="btn btn-primary">회원수정완료</button>
</div>

<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>

