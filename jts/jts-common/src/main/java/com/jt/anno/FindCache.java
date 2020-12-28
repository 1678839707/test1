package com.jt.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/*
 * 该注解要实现查询操作描述
 * 有缓存查询缓存，没有缓存就查询数据库
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FindCache {
	/*
	 * 操作规范描述：
	 * 	key:
	 * 		1、如果用户没有赋值：如果key为"",表示用户使用自动生成的key的值；
	 * 			key:是通过“包名.类名.方法名.拼接第一个参数”（有参数的情况下）
	 * 			key:是通过“包名.类名.方法名”（没有参数的情况下）
	 * 		2、如果用户赋值：就使用用户的值
	 * 	seconds:
	 * 		如果时间不为0，表示用户需要设置超时时间
	 */
	String key() default ""; //key 时redis缓存中的键值对key,
	int seconds() default 0;//设置缓存过期时间。
}
