package com.jt.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.dubbo.service.DubboUserService;
import com.jt.pojo.User;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {
	
	public static final String TICKET = "JT_TICKET";
	/*
	 * 拦截用户、登录、注册等页面。
	 */
	@RequestMapping("/{page}")
	public String doUser(@PathVariable String page) {
		return page;
	}
	/*
	 * 注册提交数据并保存
	 */
	@Reference(timeout = 3000,check = false)
	private DubboUserService dubboUserService;
	
	@ResponseBody
	@RequestMapping("/doRegister")
	public SysResult doRegister(User user) {
		if (user==null) {
			throw new IllegalArgumentException("用户注册不能为空");
		}
		dubboUserService.saveData(user);
		return SysResult.success(user.getUsername());
	}
	/*
	 * 登录表中用户名和密码的检验
	 */
	
	
	@ResponseBody
	@RequestMapping("/doLogin")
	public SysResult doLogin(User user,HttpServletResponse response) {
		//1、校验数据，校验成功后返回一个密钥，利用dubbo框架中进行处理
		String ticket = dubboUserService.findByUP(user);
		if (StringUtils.isEmpty(ticket)) {
			return SysResult.fail();
		}
		/*
		 * 关于cookie的使用
		 * cookie.setPath("/")默认全部请求信息都可见该cookie信息.
		 * www.jt.com
		 * coolie.setPath("/aa")
		 * 只有在/aa路径下的url才能访问该cookie
		 * www.jt.com/aa
		 * cookie的使用时间:
		 * cookie.setMaxAge(>0);cookie的存活时间
		 * cookie.setMaxAge(0);cookie失效
		 * cookie.setMaxAge(-1);关闭会话之后删除cookie
		 */
		
		//2、如果登录成功，把当前的ticket作为密钥，存入浏览器的cookie中
		Cookie cookie = new Cookie(TICKET, ticket);
		cookie.setMaxAge(7*24*3600);
		cookie.setPath("/");
		//设定cookie的共享
		cookie.setDomain("jt.com");
		//将cookie写入浏览器
		response.addCookie(cookie);
		return SysResult.success();
	}
	/*
	 * 退出操作
	 */
	@Autowired
	private JedisCluster jedisCluster;
	
	@RequestMapping("/logout")
	public String doLogout(HttpServletRequest request,HttpServletResponse response) {
		//获取cookie中的密钥
		Cookie[] cookies = request.getCookies();
		System.out.println("cookies:"+cookies);
		String ticket = "";
		if (cookies.length>0) {
			for (Cookie cookie : cookies) {
				if ("JT_TICKET".equals(cookie.getName())) {
					ticket = cookie.getValue();
					break;
				}
			}
		}
		//删除redis中的缓存
		if (!StringUtils.isEmpty(ticket)) {
			jedisCluster.del(ticket);
		}
		//删除cookie中的数据
		Cookie cookie = new Cookie(TICKET, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		cookie.setDomain("jt.com"); //domain 域名
		response.addCookie(cookie);
		return "redirect:/";
	}
}
