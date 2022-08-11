package com.cos.blog.test;

import org.junit.jupiter.api.Test;

import com.cos.blog.model.Reply;

public class ReplyObjectTest {

	@Test
	public void toStringTest() {
		Reply reply = new Reply().builder()
				.id(1)
				.userInfo(null)
				.board(null)
				.content("hi")
				.build();
		
		System.out.println(reply); //오브젝트 호출시에 toString이 자동으로 호출됨
	}
}
