package com.example.servlet.filterdispatchertype;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class DemoFilter implements Filter {

	private String name;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		name = filterConfig.getInitParameter("name");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		List<String> names = (List<String>) request.getAttribute("names");
		if (names == null) {
			names = new ArrayList<>();
			request.setAttribute("names", names);
		}

		names.add(name);
		chain.doFilter(request, response);
	}
}
