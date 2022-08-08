package com.cos.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

//@Getter
//@Setter
@Data
//@AllArgsConstructor  //전체변수로 constructor를 만들어준
//@RequiredArgsConstructor //final 붙은 변수만 constructor를 만들어준
@NoArgsConstructor
public class Member {
	private int id;
	private String username;
	private String password;
	private String email;
	
	@Builder
	public Member(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}
}
