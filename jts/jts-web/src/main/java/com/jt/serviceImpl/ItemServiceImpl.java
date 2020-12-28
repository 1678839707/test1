package com.jt.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.anno.FindCache;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.util.HttpClientUtil;
import com.jt.util.ObjectMapperUtil;

@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	private HttpClientUtil httpClientUtil;

	@Override
	@FindCache
	public Item findItemById(Long id) {
		String url = "http://manage.jt.com/web/item/findItemById/"+id;
		System.out.println(url);
		String entityString = httpClientUtil.doGet(url);
		//System.out.println("到这里来="+entityString);
		return ObjectMapperUtil.toObject(entityString, Item.class);
	}

	@Override
	@FindCache
	public ItemDesc findItemDescById(Long id) {
		String url = "http://manage.jt.com/web/item/findItemDescById/"+id;
		String entityString = httpClientUtil.doGet(url);
		return ObjectMapperUtil.toObject(entityString, ItemDesc.class);
	}
}
