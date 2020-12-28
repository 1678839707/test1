package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jt.anno.FindCache;
import com.jt.pojo.ItemCat;
import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;

@RestController
@RequestMapping("/item/cat/")
public class ItemCatController {
	
	@Autowired
	private ItemCatService service;
	/**
	 * url: http://localhost:8091/item/cat/queryItemName
	 * param: itemCatId
	 */
	@RequestMapping("queryItemName")	
	public String queryItemName(Long itemCatId) {
		ItemCat itemCat = service.findItemCatById(itemCatId);
		return itemCat.getName();
	}
	/**
	 * 查询一级菜单   父级id(parent_id)为0
	 * SELECT * FROM tb_item_cat WHERE parent_id=0
	 * 查询二级菜单   二级菜单的parent_id为一级菜单的id
     * SELECT * FROM tb_item_cat WHERE parent_id=1
     * 查询三级菜单   三级菜单的parent_id为二级菜单的id  
     * SELECT * FROM tb_item_cat WHERE parent_id=2
     * 
     * @ResponseBody:返回json数据
     * @RequestMapping：添加拦截路径
	 * @RequestParam(name="id",defaultValue="0",required=true)
	 * 设定请求参数：
	 * 			name/value:接收参数的名称
	 *          defaultValue:默认值
	 *          required：是否为必填
	 * @PathVariable:获取路径中的参数，restful风格里面用
	 * @Param:mybatis中，参数和xxxMapper.xml接收的参数进行匹配
	 */
	@RequestMapping("list")
	@FindCache
	public List<EasyUITree> treeList(
			@RequestParam(name="id",defaultValue="0") Long parentId){
//		if(id==null) {
//			id=0l;
//		}		
		List<EasyUITree> treeList = 
				service.findCatListById(parentId);
		return treeList;
	}
}
