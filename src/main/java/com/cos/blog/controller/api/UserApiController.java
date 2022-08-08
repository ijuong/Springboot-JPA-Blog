package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.JpaRepositoryNameSpaceHandler;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.controller.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.UserInfo;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;

	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody UserInfo userInfo) {
		userService.회원가입(userInfo);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
//	
//	//전통적인 스프링 로그인 방법 (시큐리티 사용시 사용하지 않음)
//	@PostMapping("/api/userInfo/login")
//	public ResponseDto<Integer> login(@RequestBody UserInfo userInfo, HttpSession session){
//		System.out.println("UserApiController : login호출됨");
//		UserInfo principal = userService.로그인(userInfo);  //principal 접근주체
//		
//		if (principal != null) {
//			session.setAttribute("principal", principal);
//		}
//		
//		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
//	}	
}
