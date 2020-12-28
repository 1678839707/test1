package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import com.jt.service.UserService;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster jedisCluster;
/*
 * 注册表中用户名的检验是否存在
 */
	@RequestMapping("/check/{param}/{type}")
	public JSONPObject checkUser(
			@PathVariable String  param,@PathVariable int type,String callback
			) {
		boolean flag = userService.checkUser(param,type);
		return new JSONPObject(callback,SysResult.success(flag));
	}
/*
 * 显示界面上的用户名和退出按钮
 */
	@RequestMapping("/query/{key}")
	public JSONPObject doKey(@PathVariable String key,String callback) {                 //System.out.println(key);
		String userValue = jedisCluster.get(key);                                       //System.out.println(userValue);
		return new JSONPObject(callback,SysResult.success(userValue));
	}
}
