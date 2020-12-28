package com.jt.util;


import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {
	//常量对象
	public static final ObjectMapper objectMapper = new ObjectMapper();
	
	//把对象转换成Json字符串
	public static String toJson(Object data) {
		String Json = null;
		try {
			Json = objectMapper.writeValueAsString(data);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return Json;
	}
	
//	//把Json字符串转换成对象
//	public Object toObject(String Json,Class data) {
//		Object obj = null;
//		try {
//			obj = objectMapper.readValue(Json, data.getClass());
//		}catch (IOException e) {
//			e.printStackTrace();
//		}
//		return obj;
//	}
	//方法二
	public static <T>T toObject(String Json,Class<T> data){
		T obj = null;
		try {
			obj = objectMapper.readValue(Json, data);
		}catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}
}
