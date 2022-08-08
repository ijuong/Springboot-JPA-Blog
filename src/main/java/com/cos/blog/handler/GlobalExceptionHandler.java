package com.cos.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.controller.dto.ResponseDto;

@ControllerAdvice //어디서든지 exception이 일어나면 이쪽으로 올수있다 
@RestController
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class) //	모든 exception이생기면 이쪽으로 연결됨 //개별 exception으로 설정해되됨
	public ResponseDto<String> handlerArgumentException(Exception e) {
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
	}
}
