package com.jt.Dubbo.Order.ServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.dubbo.service.OrderDubboService;
import com.jt.mapper.OrderItemMapper;
import com.jt.mapper.OrderMapper;
import com.jt.mapper.OrderShippingMapper;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.pojo.OrderShipping;

@Service
public class OrderDubboServiceImpl implements OrderDubboService{

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	
	@Override
	@Transactional
	public String insertData(Order order) {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replaceAll("-", "");
		order.setOrderId(uuid)
			.setCreated(new Date())
			.setUpdated(order.getCreated());
		orderMapper.insert(order);
		System.out.println("order插入订单数据成功");
		OrderShipping orderShipping = order.getOrderShipping();
		orderShipping.setOrderId(uuid)
					.setCreated(new Date())
					.setUpdated(orderShipping.getCreated());
		orderShippingMapper.insert(orderShipping);
		System.out.println("orderShipping插入物流数据成功");
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			orderItem.setOrderId(uuid)
				.setCreated(new Date())
				.setUpdated(orderItem.getCreated());
			orderItemMapper.insert(orderItem);
		}
		System.out.println("orderItem插入订单商品数据成功");
		return null;
	}

	@Override
	public Order queryOrderData(String id) {
		
//		Order order = orderMapper.selectById(id);
//		System.out.println("order:"+order);
//		
//		QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
//		queryWrapper.eq("order_id", id);
//		List<OrderItem> orderItems = orderItemMapper.selectList(queryWrapper);
//		
//		OrderShipping orderShipping = orderShippingMapper.selectById(id);
//		
//		order.setOrderItems(orderItems)
//			.setOrderShipping(orderShipping);
		
		Order order = orderMapper.QueryDataOrder(id);
		return order;
	}

	
	
}
