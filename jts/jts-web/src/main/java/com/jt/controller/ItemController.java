package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;

@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;

	@RequestMapping("/items/{id}")
	public String doPage(@PathVariable Long id,Model model) {
		Item item = itemService.findItemById(id);
		ItemDesc itemDesc = itemService.findItemDescById(id);
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", itemDesc);	
		return "item";
	}
//	public String doPage() {
//		return "item";
//	}
}
