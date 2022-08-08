package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.UserInfo;

//DAO
//자동으로 bean 등록이 된
//@Repostory //생략가능
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
	
	//SELECT * FROM user WHERE username = ?1;
	UserInfo findByUsername(String username);

	//전통적인 스프링 로그인 방법 (시큐리티 사용시 사용하지 않음)
	//JPA Naming query
	//SELECT * FROM user WHERE username = ?1 AND password = ?2;
	//UserInfo findByUsernameAndPassword(String username, String password);
	
	//위에것과 같은 효과
	//@Query(value = "SELECT * FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
	//UserInfo login(String username, String password);
}
