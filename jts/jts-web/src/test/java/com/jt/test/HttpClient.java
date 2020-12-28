package com.jt.test;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpClient {
	
	@Test
	public void testGet() throws IOException {
		
		//1、获取httpclient实例
		CloseableHttpClient httpClient = HttpClients.createDefault(); //closeable 可关闭的; client 客户端，客户; default 默认
		
		//2、创建httpGet请求，需要请求的url
		String url = "https://item.jd.com/100005224374.html";
		HttpGet httpGet = new HttpGet(url);
		
		//3、发起请求
		CloseableHttpResponse response = httpClient.execute(httpGet);
		
		//4、处理响应信息，响应信息里面包含状态码，处理状态码为200的信息
		if (200==response.getStatusLine().getStatusCode()) {
			String content = EntityUtils.toString(response.getEntity());
			
			//5、打印响应内容
			System.out.println(content);
		}
	}
}
