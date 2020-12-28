package com.jt.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

//访问jts-manage项目时访问这个目录
//@PropertySource("classpath:/properties/redisCluster.properties")

////访问jts-web项目时访问这个目录
@PropertySource("classpath:/properties/redis.properties")
@Configuration
public class RedisCluster {

	@Value("${redis.cluster}")
	private String url;
	
	@Bean
	public JedisCluster jedisCluster() {
		Set<HostAndPort> nodes = new HashSet<>();
		String[] urlStrings = url.split(",");
		for (String urlString : urlStrings) {
			String[] node = urlString.split(":");
			String host = node[0];
			int port = Integer.parseInt(node[1]);
			nodes.add(new HostAndPort(host, port));
		}
		return new JedisCluster(nodes);
	}
}
