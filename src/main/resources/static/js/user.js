let index = {
	init: function(){
		$("#btn-save").on("click", ()=>{
			this.save();
		});
		$("#btn-update").on("click", ()=>{
			this.update();
		});
		
		//$("#btn-login").on("click", ()=>{
		//	this.login();
		//});
	},
	
	save: function(){
		//alert('user의 save함수 호출됨');
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		
		//console.log(data);
		
		//ajax통신을 이용해서 3개의 데이터 json으로 변경해서 insert요청
		$.ajax({
			type: "POST",
			url: "/auth/joinProc",
			data: JSON.stringify(data), //http body데이터
			contentType: "application/json; charset=utf-8", //body 데이터 타입이 무엇인지 (MIME)
			dataType: "json" //응답받는 데이터가 json이라면 => javascript object로 변환해
			
		}).done(function(resp){
			console.log(resp);
			alert(resp.status);
			if(resp.status != 200) {
				alert("회원가입에 실패하였습니다");
			}else{
				alert("회원가입이 완료되었습니다");
				location.href = "/";	
			}
			
		}).fail(function(error){
			alert(JSON.stringify(error));
			
		}); 
	},
	
	update: function(){
		let data = {
			id: $("#id").val(),
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		
		$.ajax({
			type: "PUT",
			url: "/user",
			data: JSON.stringify(data), //http body데이터
			contentType: "application/json; charset=utf-8", //body 데이터 타입이 무엇인지 (MIME)
			dataType: "json" //응답받는 데이터가 json이라면 => javascript object로 변환해
			
		}).done(function(resp){
			alert(resp.status);
			if(resp.status != 200) {
				alert("회원수정이 실패하였습니다");
			}else{
				alert("회원수정이 완료되었습니다");
				location.href = "/";	
			}
		}).fail(function(error){
			alert(JSON.stringify(error));
			
		}); 
	},
	
	login: function(){
		let data = {
			username: $("#username").val(),
			password: $("#password").val()
		};
		
		/*
		$.ajax({
			type: "POST",
			url: "/api/userInfo/login",
			data: JSON.stringify(data), //http body데이터
			contentType: "application/json; charset=utf-8", //body 데이터 타입이 무엇인지 (MIME)
			dataType: "json" //응답받는 데이터가 json이라면 => javascript object로 변환해
			
		}).done(function(resp){
			alert("로그인이 완료되었습니다");
			location.href = "/";
			
		}).fail(function(error){
			alert(JSON.stringify(error));
			
		}); 
		*/
	}
}

index.init();