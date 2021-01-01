package com.example.cdi.event;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class CDIEventTest {

	@Inject
	private FooTrigger trigger;

	@Inject
	@Named("list")
	private List<Foo> list;

	@Test
	public void testEvent() throws Exception {
		Foo foo = new Foo();
		trigger.fire(foo);
		assertEquals(Collections.singletonList(foo), list);
	}

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
				.addClasses(Foo.class, FooTrigger.class, FooHandler.class, ListProvider.class);
	}
}
