package com.kevin.sp01.service;

import com.kevin.sp01.pojo.Order;

public interface OrderService {

	Order getOrder(String orderId);
	
	void addOrder(Order order);
}
