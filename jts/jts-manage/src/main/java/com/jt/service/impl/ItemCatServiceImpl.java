package com.jt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private ItemCatMapper mapper;
	
	@Override
	public ItemCat findItemCatById(Long itemCatId) {
		if(itemCatId==null||itemCatId<1) {
			throw new IllegalArgumentException("id不正确");
		}
		return mapper.selectById(itemCatId);
	}

	@Override
	public List<EasyUITree> findCatListById(Long parentId) {
		if(parentId==null||parentId<0) {
			throw new IllegalArgumentException("parentId不正确");
		}
		//条件构造器，parent_id作为查询条件
		QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("parent_id", parentId);
		List<ItemCat> itemCatList = 
				mapper.selectList(queryWrapper);
		List<EasyUITree> treeList = new ArrayList<>();
		//把数据库的记录转换成树结构记录
		for(ItemCat ic : itemCatList) {
			EasyUITree tree = new EasyUITree();
			String state = ic.getIsParent()==1?"closed":"open"; 
			tree.setId(ic.getId());
			tree.setText(ic.getName());
			tree.setState(state);
			treeList.add(tree);
		}		
		return treeList;
	}

}
