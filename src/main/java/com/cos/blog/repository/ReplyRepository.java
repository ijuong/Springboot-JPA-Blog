package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.controller.dto.ReplySaveRequestDto;
import com.cos.blog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer>{

	//인터페이스에서는 public 생략가능
	@Modifying
	@Query(value = "INSERT INTO reply (userId, boardId, content, createDate) values(?1, ?2, ?3, now())", nativeQuery = true)
	int mSave(ReplySaveRequestDto replySaveRequestDto); 
}
