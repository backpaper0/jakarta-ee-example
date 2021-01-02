package com.example.cdi;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.cdi.stereotype.StereotypeDemo;

@RunWith(Arquillian.class)
public class StereotypeTest {

	@Inject
	private StereotypeDemo demo;

	@Test
	public void testStereotype() throws Exception {
		assertEquals("Hello, Stereotype!", demo.get());
	}

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
				.addPackages(true, StereotypeDemo.class.getPackage());
	}
}
