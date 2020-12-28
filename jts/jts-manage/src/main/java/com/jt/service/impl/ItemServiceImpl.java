package com.jt.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUITable;

@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	private ItemDescMapper itemDescMapper;
	
	@Override
	public EasyUITable<Item> findPageObjects(
			Integer page, Integer rows) {
		if(page==null||page<1) {
			throw new IllegalArgumentException("页码不正确");
		}
		if(rows==null||rows<1) {
			throw new IllegalArgumentException("每页显示条数不正确");
		}
		//获取总记录数
		Integer total = itemMapper.selectCount(null);
		//获取每页记录
		Integer startIndex = (page-1)*rows;
		List<Item> itemList = itemMapper.getPageObjects(startIndex,rows);
		return new EasyUITable<>(total,itemList);
	}

	@Override
	public void saveItem(Item item,ItemDesc itemDesc) {
		if(item==null) {
			throw new IllegalArgumentException("传入值不能为空！");
		}
		//入库商品
		item.setStatus(1);
		item.setCreated(new Date());
		item.setUpdated(item.getCreated());	
		itemMapper.insert(item);			
		//mybatis-plus在新增数据时，会自动回传主键信息
		//保证主键有值
		//入库商品描述信息
		itemDesc.setItemId(item.getId());
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(itemDesc.getCreated());
		itemDescMapper.insert(itemDesc); 
	}

	@Override
	public void updateItem(Item item,ItemDesc itemDesc) {
		if(item==null) {
			throw new IllegalArgumentException("参数异常！");
		}
		item.setUpdated(new Date());
		itemMapper.updateById(item);
		
		itemDesc.setItemId(item.getId());
		itemDesc.setUpdated(new Date());
		itemDescMapper.updateById(itemDesc);
	}

	@Override
	public void deleteItems(Long[] ids) {
		if(ids==null||ids.length<1) {
			throw new IllegalArgumentException("传入的id不对");
		}
		List<Long> idList = Arrays.asList(ids);
		itemMapper.deleteBatchIds(idList);
		itemDescMapper.deleteBatchIds(idList);
	}

	@Override
	public void updateStatus(int status, Long[] ids) {
		//循环更新操作
//		Item item = new Item();
//		for(Long id:ids) {			
//			item.setId(id);
//			item.setStatus(status);
//			item.setUpdated(new Date());
//			itemMapper.updateById(item);
//		}
		//批量更新操作
		Item entity = new Item();
		entity.setStatus(status)
		      .setUpdated(new Date());
		List<Long> idList = Arrays.asList(ids);
		UpdateWrapper<Item> updateWrapper = new UpdateWrapper<>();
		updateWrapper.in("id", idList);
		itemMapper.update(entity, updateWrapper);
	}

	@Override
	public ItemDesc queryItemDescById(Long id) {
		return itemDescMapper.selectById(id);		
	}

	@Override
	public Item findItemById(Long id) {
	  return itemMapper.selectById(id);
	}

	@Override
	public ItemDesc findItemDescById(Long id) {
		return itemDescMapper.selectById(id);
	}

	
}
