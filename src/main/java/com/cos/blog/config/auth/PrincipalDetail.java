package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.blog.model.UserInfo;

import lombok.Data;
import lombok.Getter;

//스프링 시큐리티가로그인 요청을 가료채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를
// 스프링 시큐리티 고유한 세션 저장소에 저장을 해준다 
@Getter
public class PrincipalDetail implements UserDetails {

	private UserInfo userInfo; //객체를 들고있는것을 콤포지션이라고 함

	public PrincipalDetail(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	public UserInfo getUserInfo() {
		return userInfo;
	}
	
	@Override
	public String getPassword() {
		return userInfo.getPassword();
	}

	@Override
	public String getUsername() {
		return userInfo.getUsername();
	}

	//계정이 만료되지 않았는지를 리턴한다 (true : 만료안됨)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//계정이 잠겼는지 않았는지를 리턴한다 (true : 잠기지않음)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	//비밀번호 만료되지 않았는지를 리턴한다 (true : 만료안됨)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//계정이 활성화(사용가능)인지 리턴한다(true: 사용가능)
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	//계정이 어떤 타입인지 리 턴
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		/*
		collectors.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				return "ROLE_" + userInfo.getRole();
			}
		});
		*/
		// 람다식으로 표현
		collectors.add(()->{return "ROLE_" + userInfo.getRole();});
		return collectors;
	}
	
}
