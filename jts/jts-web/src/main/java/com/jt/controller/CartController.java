package com.jt.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.dubbo.service.CartDubboService;
import com.jt.pojo.Cart;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.UserThreadLocalUtil;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private JedisCluster jedisCluster;
	
	
	@Reference(timeout = 3000,check=false)
	private CartDubboService cartService;
	/*
	 * 显示当前用户的购物车
	 */
	@RequestMapping("/show")
	public String doShow(Model model,HttpServletRequest request) {
		/*
		 * 获取userId
		 * 方法一
		 */
		//Long userId = getUserId(request);                           //通过自己创建的方法获取userId
				//Long userId = 7L;
//方法二
		/*User user= (User)request.getAttribute("JS_USER");
		 * Long userId = user.getId();
		 */
//方法三
		User user = UserThreadLocalUtil.get();
		Long userId = user.getId();
		List<Cart> carts = cartService.QueryDataByUserId(userId);
		model.addAttribute("cartList",carts);
		return "cart";
	}
	
	/*
	 * 做总价(totalPrice)在随着数量的改变而随之改变
	 */
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult doUpdateNum(Cart cart,HttpServletRequest request) {
		/*
		 * 获取userId
		 * 方法一
		 */
		//Long userId = getUserId(request);                           //通过自己创建的方法获取userId
				//Long userId = 7L;
//方法二
		/*User user= (User)request.getAttribute("JS_USER");
		 * Long userId = user.getId();
		 */
//方法三
		User user = UserThreadLocalUtil.get();
		Long userId = user.getId();
		cart.setUserId(userId);
		cartService.updateNum(cart);
		return SysResult.success();
	}
	/*
	 * 删除购物车
	 */
	@RequestMapping("/delete/{itemId}")
	public String doDelete(@PathVariable Long itemId,HttpServletRequest request) {
		/*
		 * 获取userId
		 * 方法一
		 */
		//Long userId = getUserId(request);                           //通过自己创建的方法获取userId
				//Long userId = 7L;
//方法二
		/*User user= (User)request.getAttribute("JS_USER");
		 * Long userId = user.getId();
		 */
//方法三
		User user = UserThreadLocalUtil.get();
		Long userId = user.getId();
		cartService.deleteCart(userId,itemId);
		return "redirect:/cart/show.html";
	}
	/*
	 * 添加购物车
	 */
	@RequestMapping("/add/{itemId}")
	public String doAddCart(Cart cart,HttpServletRequest request) {
		/*
		 * 获取userId
		 * 方法一
		 */
		//Long userId = getUserId(request);                           //通过自己创建的方法获取userId
				//Long userId = 7L;
//方法二
		/*User user= (User)request.getAttribute("JS_USER");
		 * Long userId = user.getId();
		 */
//方法三
		User user = UserThreadLocalUtil.get();
		Long userId = user.getId();
		cart.setUserId(userId);
		cartService.addCart(cart);
		return "redirect:/cart/show.html";
	}
	
	/*
	 * 获取用户的id方法
	 */
	private Long getUserId(HttpServletRequest request) {
		//从cookie中拿去redis中的key值
				Cookie[] cookies = request.getCookies();
				String ticket = null;
				if (cookies.length>0) {
					for (Cookie cookie : cookies) {
						if ("JT_TICKET".equals(cookie.getName())) {                    //System.out.println("cookie名字:"+cookie.getName());
							ticket = cookie.getValue();                                //System.out.println("ticket:"+ticket);
						}
					}
				}
				
			//从redis中，通过保存的key值拿取到对应的user对象值。
				String userValue = jedisCluster.get(ticket);                           //System.out.println("userValue:"+userValue);
				User user = ObjectMapperUtil.toObject(userValue, User.class);          //System.out.println("user值:"+user);
			//获取userId
				Long userId = user.getId();                                            //System.out.println("userId:"+userId);
		return userId;
	}
}
