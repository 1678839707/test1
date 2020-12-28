package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;

@RestController
@RequestMapping("/web/item")
public class ItemWebController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/findItemById/{id}")
	public Item findItemById(@PathVariable Long id) {
		System.out.println("进来了");
		System.out.println(itemService.findItemById(id));
		return itemService.findItemById(id);
	}
	@RequestMapping("/findItemDescById/{id}")
	public ItemDesc findItemDescById(@PathVariable Long id) {
		return itemService.findItemDescById(id);
	}
}
