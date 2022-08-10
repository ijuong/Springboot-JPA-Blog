package com.cos.blog.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.UserInfo;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserInfoRepository;

//스프링이 컴포넌트 스캔을 통해서 bean에 등록을 해줌
@Service
public class BoardService {

	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	ReplyRepository replyRepository;
	
	@Transactional
	public void 글쓰기(Board board, UserInfo userInfo) {
		board.setCount(0);
		board.setUserInfo(userInfo);
		boardRepository.save(board);
	}

	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable){
			return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글상세보기 실패: 아이디를 찾을수 없습니다");
				});
	}
	
	@Transactional
	public void 글삭제하기(int id) {
		boardRepository.deleteById(id);
	}

	@Transactional
	public void 글수정하기(int id, Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글찾 실패: 아이디를 찾을수 없습니다");
				});//영속화 완료
		
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		
		System.out.println("@@@id : " + id);
		System.out.println("@@@getTitle : " + requestBoard.getTitle());
		System.out.println("@@@getContent : " + requestBoard.getContent());
		//해당함수 service 가 종료될때 트랜잭션 이 종료된다. 이때 더티체킹 - 자동 업데이트가 됨 db flush.
	}
	
	@Transactional
	public void 댓글쓰기(UserInfo userInfo, int boardId, Reply requestReply) {
		
		Board board = boardRepository.findById(boardId).orElseThrow(()->{
			return new IllegalArgumentException("댓글쓰기 실패: 게시글 id를 찾을수 없습니다");
		});
		requestReply.setUserInfo(userInfo); 
		requestReply.setBoard(board);
		
		replyRepository.save(requestReply);
	}
}
