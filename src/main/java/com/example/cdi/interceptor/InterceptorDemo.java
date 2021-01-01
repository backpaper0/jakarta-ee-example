package com.example.cdi.interceptor;

import javax.enterprise.context.Dependent;

@Dependent
@MyAnnotation
public class InterceptorDemo {

	public String get() {
		return "Hello, Interceptor!";
	}
}
