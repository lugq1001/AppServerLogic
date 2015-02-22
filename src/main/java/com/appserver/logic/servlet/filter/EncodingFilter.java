package com.appserver.logic.servlet.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(filterName="EncodingFilter", urlPatterns="/*")
public class EncodingFilter implements Filter {

	@Override
	public void init(FilterConfig config) throws ServletException {
		
	}
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		chain.doFilter(req, resp);
	}

	

}
