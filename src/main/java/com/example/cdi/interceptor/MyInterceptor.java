package com.example.cdi.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@MyAnnotation
@Interceptor
public class MyInterceptor {

	@AroundInvoke
	public Object invoke(InvocationContext ic) throws Exception {
		Object s = ic.proceed();
		if (s instanceof String) {
			return s + "!!";
		}
		return s;
	}
}
