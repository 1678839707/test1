package com.kevin.sp01.service;

import java.util.List;

import com.kevin.sp01.pojo.Item;

public interface ItemService {
	
	List<Item> getItems(String orderId);

	void decreaseNumders(List<Item> list);
}
