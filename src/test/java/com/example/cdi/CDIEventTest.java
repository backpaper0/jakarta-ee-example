package com.example.cdi;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.cdi.event.CDIEventDemo;
import com.example.cdi.event.Foo;

@RunWith(Arquillian.class)
public class CDIEventTest {

	@Inject
	private CDIEventDemo demo;

	@Inject
	@Named("list")
	private List<Foo> list;

	@Test
	public void testEvent() throws Exception {
		List<Foo> fooList = IntStream.range(0, 10).mapToObj(Foo::new).collect(Collectors.toList());
		demo.fire(fooList);
		assertEquals(fooList, list);
	}

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
				.addPackages(true, CDIEventDemo.class.getPackage());
	}
}
