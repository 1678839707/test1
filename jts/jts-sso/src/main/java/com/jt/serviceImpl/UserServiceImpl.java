package com.jt.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	
	@Autowired
	private UserMapper userMapper;

	/*
	 * param:用户需要检验的数据
	 * type:检验的类型 1，username,2.phone.3.email
	 */
	@Override
	public boolean checkUser(String param, int type) {
		String column = type==1?"username":(type==2?"phone":"email");
//		if (type==1) {
//			column = "username";
//		}else if (type==2) {
//			column = "phone";
//		}else {
//			column = "email";
//		}
//		boolean flag = false;
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(column, param);
		int count = userMapper.selectCount(queryWrapper);
		//有数据就是true，没有数据false
		return count>0?true:false;
	}

}
