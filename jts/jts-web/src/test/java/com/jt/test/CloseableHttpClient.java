package com.jt.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jt.util.HttpClientUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CloseableHttpClient {

	@Autowired
	private HttpClientUtil httpClient;
	
	@Test
	public void testHttpClient() {
		String resultString = httpClient.doGet("https://item.jd.com/100005224374.html", null, null);
		System.out.println(resultString);
	}
}
