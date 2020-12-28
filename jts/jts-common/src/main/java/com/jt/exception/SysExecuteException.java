package com.jt.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jt.vo.SysResult;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice   //异常通知 对controller有效
@Slf4j                  //记录日志
public class SysExecuteException {
	
	//当系统中出现运行时异常时生效
	@ExceptionHandler(RuntimeException.class)
	public SysResult error(RuntimeException e) {
		e.printStackTrace();
		log.error(e.getMessage());
		return SysResult.fail();
	}
}
