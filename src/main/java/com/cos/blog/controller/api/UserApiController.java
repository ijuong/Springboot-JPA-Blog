package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.JpaRepositoryNameSpaceHandler;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.controller.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.UserInfo;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	AuthenticationManager authenticationManager;

	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody UserInfo userInfo) {
		userService.회원가입(userInfo);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody UserInfo userInfo/*, 
			@AuthenticationPrincipal PrincipalDetail principal,
			HttpSession session*/){
		userService.회원수정(userInfo);
		
		// 세션등록
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userInfo.getUsername(), userInfo.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
				
		/* 아래는 세션을 강제로 수정하는 방법이었는데 이제 되지 않음
		// 여기서는 트랜잭션이 종료되었기 때문에 db 값은 변경이 되었음//
		//하지만 세션값은 변경이 되지 않은 상태이기 때문에 직접 세션값을 변경해 주어야 함 
		// 1. 스프링 시큐리티 authentication 을 수정해주고
		Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities()); 
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(authentication);
		//2, 세션에 스프링시큐리티 값을 넣어준다
		session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
		*/
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
	}
	
//	
//	//전통적인 스프링 로그인 방법 (시큐리티 사용시 사용하지 않음)
//	@PostMapping("/api/userInfo/login")
//	public ResponseDto<Integer> login(@RequestBody UserInfo userInfo, HttpSession session){
//		System.out.println("UserApiController : login호출됨");
//		UserInfo principal = userService.로그인(userInfo);  //principal 접근주
//		
//		if (principal != null) {
//			session.setAttribute("principal", principal);
//		}
//		
//		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
//	}	
}
