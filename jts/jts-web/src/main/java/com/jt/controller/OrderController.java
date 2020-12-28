package com.jt.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.dubbo.service.CartDubboService;
import com.jt.dubbo.service.OrderDubboService;
import com.jt.pojo.Cart;
import com.jt.pojo.Order;
import com.jt.util.UserThreadLocalUtil;
import com.jt.vo.SysResult;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Reference(timeout = 3000,check = false)
	private CartDubboService cartDubboService;
	
	@Reference(timeout = 3000,check = false)
	private OrderDubboService OrderService;
/*
 * 生成订单
 */
	@RequestMapping("/create")
	public String create(Model model) {
		Long userId = UserThreadLocalUtil.get().getId();
		List<Cart> carts = cartDubboService.QueryDataByUserId(userId);
		model.addAttribute("carts", carts);
		return "order-cart";
	}
/*
 * 保存订单	
 */
	@RequestMapping("/submit")
	@ResponseBody
	public SysResult doSubmint(Order order) {
		String orderId = OrderService.insertData(order);
		return SysResult.success(orderId);
	}
/*
 * 返回订单保存成功页面
 * http://www.jt.com/order/success.html?id=null
 */
	@RequestMapping("/success")
	public String doSuccessOrder(String id,Model model) {
		Order order =  OrderService.queryOrderData(id);
		model.addAttribute("order", order);
		return "success";
	}
}
