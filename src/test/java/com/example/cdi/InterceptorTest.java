package com.example.cdi;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.cdi.interceptor.InterceptorDemo;
import com.example.cdi.interceptor.InterceptorMethodDemo;

@RunWith(Arquillian.class)
public class InterceptorTest {

	@Inject
	private InterceptorDemo demo;

	@Inject
	private InterceptorMethodDemo methodDemo;

	@Test
	public void testInterceptor() throws Exception {
		assertEquals("Hello, Interceptor!!!", demo.get());
	}

	@Test
	public void testWithInterceptor() throws Exception {
		assertEquals("With Interceptor!!", methodDemo.withInterceptor());
	}

	@Test
	public void testNoInterceptor() throws Exception {
		assertEquals("No Interceptor", methodDemo.noInterceptor());
	}

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
				.addPackages(true, InterceptorDemo.class.getPackage())
				.addAsManifestResource("beans-interceptor.xml", "beans.xml");
	}
}
