package com.jt.dubbo.service;

import com.jt.pojo.Order;

public interface OrderDubboService {

	String insertData(Order order);

	Order queryOrderData(String id);
	
}
