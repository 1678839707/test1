package com.jt.util;

import java.util.Map;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HttpClientUtil {
	
	@Autowired
	private  RequestConfig requestConfig;
	@Autowired
	private  CloseableHttpClient httpClient;
	
	/*protopery execute 执行
	 * 用户调用工具方法
	 * 通过工具方法返回服务器的数据（json串接收）给用户
	 * 
	 * （记住，这里是用伪静态服务技术来访问，减少服务器的访问来访问静态HTML页面，所以有图片的路径，参数值，字符集）
	 * 传过来的参数有：
	 * 		url: get请求:http://www.jt.com
	 * 		路径中的参数:id=121&name=kevin;我们可以使用map<string,string>封装接收
	 * 		字符集:charset string
	 */
	
	public  String doGet(String url,Map<String, String> param,String charset) {
		//判断参数字符集是否为空，如果为空charset设置为UTF-8
		if (charset==null) {
			charset = "UTF-8";
		}
		//判断参数是否为空，如果不为空
		if (param!=null) {
			url +="?";
			for (Map.Entry<String, String > urlEntry :  param.entrySet()) {
				url += urlEntry.getKey()+"="+urlEntry.getValue()+"&";
			}
			url = url.substring(0, url.length()-1); //substring 截取字符串
		}
		//发起请求
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(requestConfig);//设定请求超时时间
		String result = null;
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (200==response.getStatusLine().getStatusCode()) {
				result = EntityUtils.toString(response.getEntity(),charset);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return result;
	}
	public String doGet(String url) {
		return doGet(url,null,null);
	}
	public String doGet(String url,Map<String, String> param) {
		return doGet(url,param,null);
	}
}
