package com.jt.dubbo.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.dubbo.service.CartDubboService;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;

@Service(timeout = 3000)
public class CartDubboServiceImpl implements CartDubboService{
	
	@Autowired
	private CartMapper cartMapper;

	@Override
	public List<Cart> QueryDataByUserId(Long userId) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", userId);
		List<Cart> cartList = cartMapper.selectList(queryWrapper);             //System.out.println(cartList);
		return cartList;
	}
/*
 * 更新cart表中的num和时间字段
 */
	@Override
	public void updateNum(Cart cart) {
		Cart entity = new Cart();
		entity.setNum(cart.getNum())
		      .setUpdated(new Date());
		UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<>();        //这里添加cart实体类，意思是实体类有的内容变成修改的条件
		updateWrapper.eq("item_id", cart.getItemId());
		updateWrapper.eq("user_id", cart.getUserId());
		cartMapper.update(entity, updateWrapper);          //entity的作用是要修改的项。  
	}
	/*
	 * 删除购物车
	 */
	@Override
	public void deleteCart(Long userId, Long itemId) {
		Map<String, Object> columnMap = new HashMap<>();
		columnMap.put("item_id", itemId);
		columnMap.put("user_id", userId);
		cartMapper.deleteByMap(columnMap );
	}
	/*
	 * 添加购物车
	 */
	@Override
	public void addCart(Cart cart) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", cart.getUserId());
		queryWrapper.eq("item_id", cart.getItemId());
		Cart cartEntity = cartMapper.selectOne(queryWrapper);           //System.out.println("显示查询数据:"+cartEntity);
		if (cartEntity!=null) {
			Cart entity = new Cart();
			entity.setNum(cart.getNum()+cartEntity.getNum());
			entity.setUpdated(new Date());
			UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<>();
			updateWrapper.eq("user_id", cart.getUserId());
			updateWrapper.eq("item_id", cart.getItemId());
			cartMapper.update(entity, updateWrapper );                  //System.out.println("到这里看看修改");
		}else {
			cart.setCreated(new Date())
			.setUpdated(new Date());
			cartMapper.insert(cart);                                    //System.out.println("我可以插入数据");
		}
	}

	
}
