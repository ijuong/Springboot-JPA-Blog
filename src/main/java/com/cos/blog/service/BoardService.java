package com.cos.blog.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.cos.blog.controller.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.UserInfo;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

//스프링이 컴포넌트 스캔을 통해서 bean에 등록을 해줌
@Service
@RequiredArgsConstructor
public class BoardService {
	
	//autowired 를 사용안하고 클래스맨위에 requiredargsconstructor를 쓰면 아래처럼 final 로 변수선언을 할수 있다 
	//(같은방법) 
	private final BoardRepository boardRepository;
	private final UserInfoRepository userInfoRepository;	
	private final ReplyRepository replyRepository;
	
	

	//@Autowired
	//BoardRepository boardRepository;
	
	//@Autowired
	//UserInfoRepository userInfoRepository;
	
	//@Autowired
	//ReplyRepository replyRepository;
	
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
	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {		
		
		//native query 로 바로인서트
		replyRepository.mSave(replySaveRequestDto);
		
		//아래는 객체를 영속화 하여 jpa 인서트 
		/*
		UserInfo userInfo = userInfoRepository.findById(replySaveRequestDto.getUserId()).orElseThrow(()->{
			return new IllegalArgumentException("댓글쓰기 실패: 작성 id를 찾을수 없습니다");
		});
		
		Board board = boardRepository.findById(replySaveRequestDto.getBoardId()).orElseThrow(()->{
			return new IllegalArgumentException("댓글쓰기 실패: 게시글 id를 찾을수 없습니다");
		});
				
//		아래가 모델에 update 메소드를 만들어서 더 간편하게 할수 있다.
//		Reply reply = Reply.builder()
//				.content(replySaveRequestDto.getContent())
//				.userInfo(userInfo)
//				.board(board)
//				.build();
		Reply reply = new Reply();
		reply.update(userInfo, board, replySaveRequestDto.getContent());
		
		replyRepository.save(reply);
		*/
	}
	
	@Transactional
	public void 댓글삭제(int replyId) {
		replyRepository.deleteById(replyId);
	}
}
