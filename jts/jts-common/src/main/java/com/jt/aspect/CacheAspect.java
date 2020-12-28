package com.jt.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.jt.anno.FindCache;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;


@Aspect
@Component //表示spring自动生成bean，然后并加载这个类。进行实例化。
public class CacheAspect {
	//加入缓存 (required 必须的，需要的)
//	@Autowired(required = false)
//	private Jedis jedis;
	
	//redis缓存中添加集群并进行切面
	@Autowired
	private JedisCluster jedis;

	@SuppressWarnings("unchecked")
	@Around("@annotation(findCache)")
	public Object around(ProceedingJoinPoint joinPoint,FindCache findCache) { //proceeding 程序，进行，事项;join 参加，加入，连接；point 点，要点，标点 
		System.out.println("进入切面了;"+findCache.key()+";"+findCache.seconds());
		//拿到key
		String key = getKey(joinPoint,findCache); 
		System.out.println("key="+key);
		//1、从缓存中拿数据
		String result = jedis.get(key);
		//2、判断缓存中是否有数据
		Object data = null;
		try {
		if (StringUtils.isEmpty(result)) {
			//2.1、缓存中没有数据，从目标方法中拿数据
			/*
			 * joinPoint.proceed();方法表示从切入点处开始要执行的业务了；换句话就是这个时候就开始执行切入点一下的内容。
			 * 然后把数据返回到切面，再然后有切面把数据返回到medol层，再把数据返回到前端控制器，再由前端控制器返回到view层进行jsp渲染，然后返回给客户。
			 */
				data = joinPoint.proceed();                  //proceed 开始，继续进行，发生
				//2.2、将目标方法返回值转换为json字符串
				String json = ObjectMapperUtil.toJson(data);
				//2.3、判断用户是否有编辑超时时间
				int seconds = findCache.seconds();           // seconds 秒钟
				if (findCache.seconds()>0) {
					//2.3.1、如果有超时时间就设置进去
					jedis.setex(key, seconds, json);
				}else {
					//2.3.2、没有就直接返回
					jedis.set(key, json);
				}
				System.out.println("从数据库中取数据");
			}else {
				//3、缓存不为空时，将缓存数据转换为对象
				Class returnType = getReturnClass(joinPoint);
				data = ObjectMapperUtil.toObject(result, returnType);
				System.out.println("从缓存中取数据");
			}
		} catch (Throwable e) {
				e.printStackTrace();
			}
		return data;
	}

	private Class getReturnClass(ProceedingJoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();  //signature 签名，信号
		return signature.getReturnType();
	}
	//这个方法是定义key的值是唯一的值
	private String getKey(ProceedingJoinPoint joinPoint, FindCache findCache) {
		String key = findCache.key();
		if (StringUtils.isEmpty(key)) { //empty 空的
			//生成key，key=包名.类名.方法名.第一个参数
			//拿包+类名
			String className = joinPoint.getSignature().getDeclaringTypeName(); //declaring 宣告，声明； signature 签名，信号
			System.out.println(className);
			//拿方法名
			String methodName = joinPoint.getSignature().getName();
			System.out.println(methodName);
			//拿参数的值
			Object[] args = joinPoint.getArgs();
			System.out.println("Object[]="+args[0]);
			//拼装成key
			if (args.length>0) {
				key = className+"."+methodName+":"+args[0];
			}else {
				key = className+"."+methodName;
			}
		}
		return key;
	}
}
