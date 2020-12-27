package com.example.helloworld.jpa;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class HelloWorldJPATest {

	@Inject
	private HelloWorldJPA sut;

	@Test
	public void testJPA() throws Exception {
		Demo demo = sut.create("Hello, JPA!");
		Long id = demo.getId();
		assertNotNull(id);
		assertEquals("Hello, JPA!", demo.getMessage());

		demo = sut.find(id);
		assertNotNull(id);
		assertEquals("Hello, JPA!", demo.getMessage());

		sut.remove(id);

		demo = sut.find(id);
		assertNull(demo);
	}

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
				.addClasses(HelloWorldJPA.class, Demo.class)
				.addAsResource("META-INF/persistence.xml");
	}
}
