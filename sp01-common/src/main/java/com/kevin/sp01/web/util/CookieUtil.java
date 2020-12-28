package com.kevin.sp01.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

	public static void setCookie(HttpServletResponse response,String name,String value,String domain,String path,int expiry) {
		
		Cookie cookie = new Cookie(name, value);
		
		if (domain!=null) {
			cookie.setDomain(domain);
		}
		
		cookie.setPath(path);
		
		cookie.setMaxAge(expiry);
		
		response.addCookie(cookie);
		
	}
	
	public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
		setCookie(response, name, value, null, "/", maxAge);
	}
	public static void setCookie(HttpServletResponse response, String name, String value) {
		setCookie(response, name, value, null, "/", 3600);
	}
	public static void setCookie(HttpServletResponse response, String name) {
		setCookie(response, name, "", null, "/", 3600);
	}

	public static String  getCookie(HttpServletRequest request,String name) {
		
		Cookie[] cookies = request.getCookies();
		
		String value = null;
		
		if (cookies.length>0) {
			for(Cookie cookie :cookies) {
				if (name.equals(cookie.getName())) {
					value = cookie.getValue();
					break;
				}
			}
		}
		
		return value;
	}
	
	public static void removeCookie(HttpServletResponse response, String name, String domain, String path) {
		setCookie(response, name, "", domain, path, 0);
	}

}
