package com.cos.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.UserInfo;
import com.cos.blog.repository.UserInfoRepository;

//스프링이 컴포넌트 스캔을 통해서 bean에 등록을 해줌
@Service
public class UserService {

	@Autowired
	UserInfoRepository userInfoRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional
	public void 회원가입(UserInfo userInfo) {
		
		String rawPassword = userInfo.getPassword();
		String encPassword = encoder.encode(rawPassword);
		
		userInfo.setPassword(encPassword);
		userInfo.setRole(RoleType.USER);
		userInfoRepository.save(userInfo);
	}
	
	@Transactional(readOnly = true)
	public UserInfo 회원찾기(String username) {
		UserInfo userInfo = userInfoRepository.findByUsername(username);
		
		return userInfo;
	}
	
	@Transactional
	public void 회원수정(UserInfo userInfo) {
		UserInfo persistance = userInfoRepository.findById(userInfo.getId())
				.orElseThrow(()->{
					return new IllegalArgumentException("회원찾기실패");
				});
		
		// validation check
		if(persistance.getOauth() == null || persistance.getOauth().equals("")) {
			String rawPassword = userInfo.getPassword();
			String encPassword = encoder.encode(rawPassword);
			persistance.setPassword(encPassword);			
			persistance.setEmail(userInfo.getEmail());
		}
		
		//회원수정 종료 -> 서비스 종료 -> 트랜잭션 종료 -> commit 이 자동으로 된다 (더티체킹)
	}
	
	//전통적인 스프링 로그인 방법 (시큐리티 사용시 사용하지 않음)
	//@Transactional(readOnly = true) //select 할때 트랜잭션 시작, 서비스종료시 트랜잭션 종료(정합성 유지)	
	//public UserInfo 로그인(UserInfo userInfo) {
	//	return userInfoRepository.findByUsernameAndPassword(userInfo.getUsername(), userInfo.getPassword());
	//}
	
}
