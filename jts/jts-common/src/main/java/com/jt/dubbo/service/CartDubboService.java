package com.jt.dubbo.service;

import java.util.List;

import com.jt.pojo.Cart;

public interface CartDubboService {

	List<Cart> QueryDataByUserId(Long userId);

	void updateNum(Cart cart);

	void addCart(Cart cart);

	void deleteCart(Long userId, Long itemId);

}
