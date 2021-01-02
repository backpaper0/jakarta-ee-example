package com.example.cdi;

import static org.junit.Assert.*;

import javax.enterprise.inject.spi.Extension;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.cdi.extension.Bar;
import com.example.cdi.extension.ExtensionDemo;
import com.example.cdi.extension.Foo;

@RunWith(Arquillian.class)
public class ExtensionTest {

	@Inject
	private Foo foo;
	@Inject
	private Bar bar;

	@Test
	public void testFoo() throws Exception {
		assertEquals("foo", foo.get());
	}

	@Test
	public void testBar() throws Exception {
		assertEquals("bar", bar.get());
	}

	@Deployment
	public static WebArchive createDeployment() {

		JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
				.addClass(ExtensionDemo.class)
				.addAsResource(new StringAsset(ExtensionDemo.class.getName()),
						"META-INF/services/" + Extension.class.getName());

		return ShrinkWrap.create(WebArchive.class)
				.addClasses(Foo.class, Bar.class)
				.addAsLibraries(jar);
	}
}
