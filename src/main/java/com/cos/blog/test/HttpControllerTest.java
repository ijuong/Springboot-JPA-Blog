package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//사용자가요청 -> 응답(html) 
//@Controller
//사용자가요청 -> 응답(data)
@RestController
public class HttpControllerTest {

	public static final String TAG = "HttpControllerTest";
	
	@GetMapping("/http/get")
	public String getTest(Member m) {
		return "get 요청 : " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
	}
	
	@PostMapping("/http/post")
	public String postTest(@RequestBody Member m) { //messageConverter(springboot)
		return "post 요청 : " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
	}
	
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청 : " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
	}
	
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
	
	@GetMapping("/http/lombok")
	public String lombokTest() {
		//Member m = new Member(1, "이주용", "1234", "ijuong@naver.com");
		Member m = Member.builder().username("이주용").password("1234").email("ijuong@naver.com").build();
		System.out.println(TAG + "getter : " + m.getUsername());
		m.setUsername("홍길동");
		System.out.println(TAG + "setter : " + m.getUsername());
		return "lombok test 완료";
	}
}
