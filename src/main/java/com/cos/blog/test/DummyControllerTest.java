package com.cos.blog.test;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.UserInfo;
import com.cos.blog.repository.UserInfoRepository;

@RestController //html이아닌 데이터를 리턴해주는 어노테이션
public class DummyControllerTest {
	
	@Autowired //의존성주입(DI)
	private UserInfoRepository userInfoRepository;
	
	@DeleteMapping("/dummy/userinfo/{id}")
	public String deleteUser(@PathVariable int id) {
		try {
			userInfoRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
			return "삭제에 실패하였습니다. 해당 id는 db에 없습니다";
		}
		
		return "삭제되었습니다. id : " + id;
	}
	
	
	// email, password update
	@Transactional // 함수종료시 자동 commit 이 된다 
	@PutMapping("/dummy/userinfo/{id}")
	public UserInfo updateUser(@PathVariable int id, @RequestBody UserInfo requestUser) {//json 데이터로 요청 --> 자바 오브젝트로 messageconverter가jackson 라이브러리를 사용하여 자동으로 변환해
		System.out.println("id : " + id);
		System.out.println("password : " + requestUser.getPassword());
		System.out.println("email : " + requestUser.getEmail());
		
		UserInfo userInfo = userInfoRepository.findById(id).orElseThrow(()->{
			return new  IllegalArgumentException("수정에 실팽하였습니다");
		});
		
		userInfo.setPassword(requestUser.getPassword());
		userInfo.setEmail(requestUser.getEmail());
		
		//@Transational 어노테이션이 있으면 save가 없어도 저장이  된다 //더티 체킹을 통한 업데이트 (자동으로 변경사항이 있으면 업데이트함) 
		//userInfoRepository.save(userInfo);
		
		return userInfo;
	}
	//한페이지당 2건씩 리턴
	@GetMapping("/dummy/userinfo")
	public List<UserInfo> pageList(@PageableDefault(size=2, direction = Sort.Direction.DESC) Pageable pageable) {
		Page<UserInfo> pagingUser = userInfoRepository.findAll(pageable);
		List<UserInfo> userInfo = pagingUser.getContent();
		return userInfo;
	}
	
	@GetMapping("/dummy/userinfos")
	public List<UserInfo> list() {
		return userInfoRepository.findAll();
	}
	
	@GetMapping("/dummy/userinfo/{id}")
	public UserInfo detail(@PathVariable int id) {
		//없는id로 검색할경우 db에서 못찾으면 userinfo는 null이
		//그럼 return은 null이되
		//Optional로 userinfo를 감싸서테니 null확인후 return
		
		/*
		UserInfo userInfo = userInfoRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 유저는 없습니다. id: " + id);
			}
		});
		*/
		
		//위 코드 람다식으로 변경
		UserInfo userInfo = userInfoRepository.findById(id).orElseThrow(()->{
			return new  IllegalArgumentException("해당 유저는 없습니다. id: " + id);
		});
		
		//요청 웹브라우저 userinfo 객체는 자바오브젝트
		//gson 라이브러리 같이 웹브라우저가 이해할수 있는데이터로 변환을 해줘야 하
		//스프링부트에서는 자바오브젝트를 리턴하면 messageconverter가 jackson라이브러리를 호출해 json으로 자동변환하여 러턴
		return userInfo;
	}

	@PostMapping("/dummy/join")
	public String join(UserInfo userInfo) {
		System.out.println("username : " + userInfo.getUsername());
		System.out.println("password : " + userInfo.getPassword());
		System.out.println("email : " + userInfo.getEmail());
		
		userInfo.setRole(RoleType.USER);
		userInfoRepository.save(userInfo);
		
		return "회원가입이 완료되었습니다";
	}
}
