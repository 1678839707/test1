package com.jt.dubbo.service;

import org.springframework.transaction.annotation.Transactional;

import com.jt.pojo.User;

public interface DubboUserService {

	@Transactional
	void saveData(User user);

	String findByUP(User user);
	
	

}
