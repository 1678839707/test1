package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.service.GetPagService;

@Controller
public class GetPageController {

	@Autowired
	private GetPagService getPageService;
	
	@RequestMapping("/getPage")
	@ResponseBody
	public String doGetPage(Integer id) {
		return getPageService.getQueryPage(id);
	}
}
