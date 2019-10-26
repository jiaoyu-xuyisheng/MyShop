package com.jiaoyu.myfilter;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jiaoyu.domain.user;
import com.jiaoyu.server.UserService;

public class AutoLoginFilter implements Filter {

    public AutoLoginFilter() {
      
    }

	
	

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;//类型转换不能用同名变量;
		HttpServletResponse res=(HttpServletResponse)response;
		HttpSession session=req.getSession();
		user use = (user) session.getAttribute("use");
		if(use==null) {
		String cookie_username=null;
		String cookie_password=null;
		Cookie[] cookies=req.getCookies();
		if(cookies!=null) {
			for (Cookie cookie : cookies) {
				if("cook_username".equals(cookie.getName())) {
					cookie_username=cookie.getValue();
					cookie_username=URLDecoder.decode(cookie_username, "UTF-8");
				}
				if("cook_password".equals(cookie.getName())) {
					cookie_password=cookie.getValue();
				}
			}
		}
		
		if(cookie_username!=null&&cookie_password!=null) {
			UserService service=new UserService();
			try {
				 use=service.login(cookie_username, cookie_username);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
			session.setAttribute("use", use);
		}
		
		chain.doFilter(request, response);
	}

	public void destroy() {
		
	}
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
