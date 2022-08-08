package com.cos.blog.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.blog.model.UserInfo;
import com.cos.blog.repository.UserInfoRepository;

@Service
public class PrincipalDetailService implements UserDetailsService{

	@Autowired
	private UserInfoRepository userInfoRepository;
	
	//스프링이 로그인 요청을 가로챌때 username, password 2개를 가로채는데
	// password부분은 알아서 처리함
	// username이 db에 있는지 확인해주면 됨
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("@@@@@ username :" +username);
		UserInfo userInfo = userInfoRepository.findByUsername(username);
		
		return new PrincipalDetail(userInfo);
	}
}
