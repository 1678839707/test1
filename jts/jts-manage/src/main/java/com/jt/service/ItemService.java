package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;

public interface ItemService {

	EasyUITable<Item> findPageObjects(Integer page, Integer rows);

	void saveItem(Item item,ItemDesc itemDesc);

	void updateItem(Item item,ItemDesc itemDesc);

	void deleteItems(Long[] ids);

	void updateStatus(int status, Long[] ids);

	ItemDesc queryItemDescById(Long id);

	Item findItemById(Long id);

	ItemDesc findItemDescById(Long id);	
	
}
