package com.jiaoyu.myfilter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jiaoyu.domain.user;

public class limiteUserFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse res=(HttpServletResponse) response;
		HttpSession session = req.getSession();
		user use = (user) session.getAttribute("use");
		if(use==null) {
			res.sendRedirect(req.getContextPath()+"/login.jsp");
			return;
		}
		
		chain.doFilter(req, res);
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
	
	}
	
	public limiteUserFilter() {
		
	}

	public void destroy() {
		
	}

	

}
