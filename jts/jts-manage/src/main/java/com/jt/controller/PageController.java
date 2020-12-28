package com.jt.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//@PropertySource("classpath:/application.yml")
public class PageController {
	//集群方法二
	@Value("${server.port}")
	private String port;
	
	@RequestMapping("/getMsg")
	@ResponseBody
	public String getMsg() {
		return "我是"+port+"主机";
	}
	
	//集群方法一
//	@RequestMapping("/getMsg")
//	@ResponseBody
//	public String getMsg() {
//		return "我是8083";
//	}
	
	@RequestMapping("/page/{pageName}")
	public String toPage(@PathVariable String pageName) {
		return pageName;
	}
	
}
