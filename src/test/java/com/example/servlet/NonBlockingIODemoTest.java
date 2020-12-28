package com.example.servlet;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import javax.servlet.http.HttpServletResponse;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class NonBlockingIODemoTest {

	@RunAsClient
	@Test
	public void testNonBlockingIO(@ArquillianResource URL resource) throws Exception {
		URI uri = new URL(resource, "non-blocking-io").toURI();

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		assertEquals(HttpServletResponse.SC_OK, response.statusCode());

		assertEquals("text/plain", response.headers().firstValue("Content-Type").get());

		assertEquals("Hello, Non Blocking IO!", response.body());
	}

	@Deployment(testable = false)
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "ROOT.war")
				.addClass(NonBlockingIODemo.class);
	}
}
