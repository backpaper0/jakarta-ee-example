package com.example.cdi.interceptor;

import javax.enterprise.context.Dependent;

@Dependent
public class InterceptorMethodDemo {

	@MyAnnotation
	public String withInterceptor() {
		return "With Interceptor";
	}

	public String noInterceptor() {
		return "No Interceptor";
	}
}
