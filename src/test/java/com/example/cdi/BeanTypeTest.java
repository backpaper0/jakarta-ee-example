package com.example.cdi;

import static org.junit.Assert.*;

import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.cdi.beantype.Bar;
import com.example.cdi.beantype.BarImpl;
import com.example.cdi.beantype.Foo;
import com.example.cdi.beantype.FooBarImpl;
import com.example.cdi.beantype.FooImpl;

@RunWith(Arquillian.class)
public class BeanTypeTest {

	@Inject
	private BeanManager bm;

	@Test
	public void testFoo() throws Exception {
		var classes = bm.getBeans(Foo.class)
				.stream()
				.map(Bean::getBeanClass)
				.collect(Collectors.toSet());
		assertEquals(Set.of(FooImpl.class, FooBarImpl.class), classes);
	}

	@Test
	public void testBar() throws Exception {
		var classes = bm.getBeans(Bar.class)
				.stream()
				.map(Bean::getBeanClass)
				.collect(Collectors.toSet());
		assertEquals(Set.of(BarImpl.class, FooBarImpl.class), classes);
	}

	@Test
	public void testTyped() throws Exception {
		assertEquals(1, bm.getBeans(FooImpl.class).size());
		assertEquals(0, bm.getBeans(BarImpl.class).size());
	}

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
				.addPackages(true, FooBarImpl.class.getPackage());
	}
}
