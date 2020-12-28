package com.jt.controller;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.ItemDesc;
import com.jt.util.ObjectMapperUtil;

@RestController
public class JSONPController {

	@RequestMapping("/web/testJSONP")
	public JSONPObject toJSON(String callback) {
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(23243L).setItemDesc("json测试").setCreated(new Date()).setUpdated(new Date());
		//String json = ObjectMapperUtil.toJson(itemDesc);
		return new JSONPObject(callback, itemDesc);
	}
}
