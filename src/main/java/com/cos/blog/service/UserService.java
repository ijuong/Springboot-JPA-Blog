package com.cos.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
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
	
	//전통적인 스프링 로그인 방법 (시큐리티 사용시 사용하지 않음)
	//@Transactional(readOnly = true) //select 할때 트랜잭션 시작, 서비스종료시 트랜잭션 종료(정합성 유지)	
	//public UserInfo 로그인(UserInfo userInfo) {
	//	return userInfoRepository.findByUsernameAndPassword(userInfo.getUsername(), userInfo.getPassword());
	//}
	
}
