package com.jt.dubbo.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.dubbo.service.DubboUserService;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.JedisCluster;

@Service(timeout = 3000)
public class UserDubboServiceImpl implements DubboUserService {
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private JedisCluster jedisCluster;

	@Override
	public void saveData(User user) {
		String md5pwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5pwd);
		user.setEmail(user.getPhone()).setCreated(new Date()).setUpdated(user.getCreated());
		userMapper.insert(user);
		System.out.println("用户数据保存了");
	}

	@Override
	public String findByUP(User user) {
		//为了与数据库的密码一致，需要将密码加密
		String md5pwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5pwd);
		QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
		//根据用户名和密码校验数据
		User selectOneUser = userMapper.selectOne(queryWrapper);
		String ticket = null;
		if (selectOneUser!=null) {
			//生成密码，并把对象转换成json串，在存入redis中
			String uuid = UUID.randomUUID().toString();
			ticket = DigestUtils.md5DigestAsHex(uuid.getBytes());
			//json串
			//对密码进行脱敏处理
			String password = "123456你信吗!";
			selectOneUser.setPassword(password);
			String userJson = ObjectMapperUtil.toJson(selectOneUser);
			//存入redis
			jedisCluster.setex(ticket, 7*24*3600, userJson);
			
		}
		return ticket;
	}

}
