package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUITable;
import com.jt.vo.SysResult;

@RestController
@RequestMapping("/item/")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping("query")
	public EasyUITable<Item> findPageObject(Integer page,Integer rows){		
		return itemService.findPageObjects(page,rows);
	}
	/**
	 * http://localhost:8091/item/save
	 * 
	 * 获取参数名称：通过反射实例化对象，
	 *     调用getXxx~~~get去掉~~~Xxx 首字母小写~~xxx
	 *     
	 * 取值：request.getParameter(xxx);
	 * 赋值：对象.setXxx(value);   
	 */
	@RequestMapping("save")
	public SysResult save(Item item,ItemDesc itemDesc) {
		itemService.saveItem(item,itemDesc);
		return SysResult.success();
	}
	/**
	 * http://localhost:8091/item/update
	 * 参数：Item
	 */
	@RequestMapping("update")
	public SysResult update(Item item,ItemDesc itemDesc) {
		itemService.updateItem(item,itemDesc);
		return SysResult.success();
	}
	/**
	 * http://localhost:8091/item/delete
	 * 参数:ids数组
	 */
	@RequestMapping("delete")
	public SysResult delete(Long[] ids) {
		itemService.deleteItems(ids);
		return SysResult.success();
	}
	/**
	 * item/reshelf   item/instock
	 * 参数：ids数组
	 */
//	@RequestMapping("{moduleName}")
//	public SysResult updateStatus(
//			@PathVariable("moduleName") String moduleName,
//			Long[] ids) {
//		//上架操作
//		if("reshelf".equals(moduleName)) {
//			
//		}else if("instock".equals(moduleName)) {
//			
//		}else {
//			return SysResult.fail();
//		}		
//		return SysResult.success();
//	}
	@RequestMapping("reshelf")
	public SysResult reshelf(Long[] ids) {
		int status=1;
		itemService.updateStatus(status,ids);
		return SysResult.success();
	}
	@RequestMapping("instock")
	public SysResult instock(Long[] ids) {
		int status=2;
		itemService.updateStatus(status,ids);
		return SysResult.success();
	}
	/**
	 *   item/query/item/desc/1474391966
	 *   参数：id=1474391962
	 *   返回值：SysResult(data)
	 */
	@RequestMapping("query/item/desc/{id}")
	public SysResult queryItemDescById(
			@PathVariable("id") Long id) {
		ItemDesc itemDesc = 
				itemService.queryItemDescById(id);
		return SysResult.success(itemDesc);
	}
	
}
