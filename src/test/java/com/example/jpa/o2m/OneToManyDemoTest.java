package com.example.jpa.o2m;

import static org.junit.Assert.*;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.HashEqualsToString;
import com.example.jpa.JpaProviders;

@RunWith(Arquillian.class)
public class OneToManyDemoTest {

	@Inject
	private OneToManyDemo demo;
	@Inject
	private UserTransaction utx;

	@Test
	public void selectFoo() throws Exception {
		{
			utx.begin();
			Foo actual = demo.selectFoo(1);
			Foo expected = new Foo(1, List.of(new Bar(3), new Bar(4)));
			assertEquals(expected, actual);
			utx.commit();
		}
		{
			utx.begin();
			Foo actual = demo.selectFoo(2);
			Foo expected = new Foo(2, List.of());
			assertEquals(expected, actual);
			utx.commit();
		}
		{
			utx.begin();
			assertThrows(NoResultException.class, () -> demo.selectFoo(3));
			utx.commit();
		}
	}

	@Test
	public void selectFooByBarId() throws Exception {
		{
			utx.begin();
			Foo actual = demo.selectFooByBarId(3);
			Foo expected = new Foo(1, List.of(new Bar(3), new Bar(4)));
			assertEquals(expected, actual);
			utx.commit();
		}
		{
			utx.begin();
			assertThrows(NoResultException.class, () -> demo.selectFooByBarId(1));
			utx.commit();
		}
	}

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap
				.create(JavaArchive.class)
				.addPackage(OneToManyDemo.class.getPackage())
				.addClasses(HashEqualsToString.class, JpaProviders.class)
				.addAsResource("META-INF/persistence.xml");
	}
}
