package com.jt.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

//只能用于jts-manage(管理，经理)项目访问
//@PropertySource("classpath:/properties/shardedJedis.properties")

@Configuration
public class ShardedJedisConfig {

	//@Value("${redis.url}")
//	private String url;
//	
//	@Bean
//	public ShardedJedis shardedJedis() {
//		List<JedisShardInfo> shards = new ArrayList<>();
//		String[] urlString = url.split(",");
//		for (String urls : urlString) {
//			String[] urlStrings = urls.split(":");
//			String host = urlStrings[0];
//			int port = Integer.parseInt(urlStrings[1]);
//			JedisShardInfo  jedisShardInfo = new JedisShardInfo(host,port);
//			shards.add(jedisShardInfo);
//		}
//		return new ShardedJedis(shards );
//	}
}
