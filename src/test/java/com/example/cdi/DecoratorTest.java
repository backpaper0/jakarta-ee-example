package com.example.cdi;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.cdi.decorator.DecoratorDemo;

@RunWith(Arquillian.class)
public class DecoratorTest {

	@Inject
	private DecoratorDemo demo;

	@Test
	public void testDecorator() throws Exception {
		assertEquals("Hello, Decorator!!!", demo.get());
	}

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
				.addPackages(true, DecoratorDemo.class.getPackage())
				.addAsManifestResource("beans-decorator.xml", "beans.xml");
	}
}
