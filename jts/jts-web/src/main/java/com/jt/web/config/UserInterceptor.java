package com.jt.web.config;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.UserThreadLocalUtil;

import redis.clients.jedis.JedisCluster;
@Component
public class UserInterceptor implements HandlerInterceptor { //handler 处理器，interceptor 拦截器
	
	@Autowired
	private JedisCluster jedisCluster;
	
	public static final String TICKET = "JT_TICKET";
	

/*
 * 方法说明:
 * 	1、boolean:
 * 		true:放行
 * 		false:拦截，拦截到指定页面，配合重定向使用
 * 	  实现步骤：
 * 		1、获取用户的cookie信息，获取密钥；
 * 		2、从redis中获取数据
 * 
 *  2、preHandle 是在发送过来的请求还没有到web项目服务器时进行拦截
 *  3、postHandle 是在web项目服务器响应客户端时，刚发出web项目服务器时进行拦截
 *  4、afterCompletion 是在web项目服务器发出，客户端还没有收到的中间进行拦截，就是在客服端马上要接收到时，进行拦截。
 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//获取cookie信息
		Cookie[] cookies = request.getCookies();
		String ticket = null;
		if (cookies.length>0) {
			for (Cookie cookie : cookies) {
				if (TICKET.equals(cookie.getName())) {
					ticket = cookie.getValue();
					break;
				}
			}
		}
		if (!StringUtils.isEmpty(ticket)) {
			//通过密钥从redis中获取user数据
			String userJson = jedisCluster.get(ticket);
			if (!StringUtils.isEmpty(userJson)) {
				User user = ObjectMapperUtil.toObject(userJson, User.class);
				//request.setAttribute("JT_USER", user);
				UserThreadLocalUtil.set(user);
				return true;
			}
		}
		//如果 用户没有登录需要重定向到登录页面
		response.sendRedirect("/user/login.html");  //send 发送
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		UserThreadLocalUtil.remove();
	}

}
