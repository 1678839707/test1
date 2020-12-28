package com.jt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.service.GetPagService;
import com.jt.util.ObjectMapperUtil;

@Service
public class GetPageServiceImpl implements GetPagService {
	
	@Autowired
	private ItemMapper itemMapper;

	@Override
	public String getQueryPage(Integer id) {
		Item selectById = itemMapper.selectById(id);
		String entity = ObjectMapperUtil.toJson(selectById);
		return entity;
	}

}
