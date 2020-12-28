package com.jt.util;

import com.jt.pojo.User;

public class UserThreadLocalUtil {
	
	private static ThreadLocal<User> threadLocal = new ThreadLocal<>();
	
	public static void set(User user) {
		threadLocal.set(user);
	}
	
	public static User get() {
		return threadLocal.get();
	}
	/*
	 * 防止内存溢出
	 */
	public static void remove() {
		threadLocal.remove();
	}
}
