package com.jt.service;

import java.util.List;

import com.jt.pojo.ItemCat;
import com.jt.vo.EasyUITree;

public interface ItemCatService {

	ItemCat findItemCatById(Long itemCatId);
	/**
	 * 通过parentId查询目录
	 * @param parentId
	 * @return
	 */
	List<EasyUITree> findCatListById(Long parentId);

}
