package com.jt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.Jedis;

//只能用于jts-manage(管理,经理)项目访问redis缓存
//@PropertySource("classpath:/properties/redis.propertly")

@Configuration
public class RedisConfig {

	//@Value("${redis.host}")
	String host;
	//@Value("${redis.port}")
	int port;
	
	@Bean
	public Jedis jedis() {
		return new Jedis(host,port);
	}
	
}
