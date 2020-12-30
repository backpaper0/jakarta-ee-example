package com.example.servlet;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.servlet.http.HttpServletResponse;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class METAINFResourcesDemoTest {

	@RunAsClient
	@Test
	public void testMETAINFResources(@ArquillianResource URL resource) throws Exception {
		URI uri = new URL(resource, "index.html").toURI();

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		assertEquals(HttpServletResponse.SC_OK, response.statusCode());

		assertEquals("text/html", response.headers().firstValue("Content-Type").get());

		String expected = Files.readString(Path.of("src/main/resources/index.html"));
		assertEquals(expected, response.body());
	}

	@RunAsClient
	@Test
	public void testServlet(@ArquillianResource URL resource) throws Exception {
		URI uri = new URL(resource, "helloworld").toURI();

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		assertEquals(HttpServletResponse.SC_OK, response.statusCode());

		assertEquals("text/html", response.headers().firstValue("Content-Type").get());

		String expected = Files.readString(Path.of("src/main/resources/index.html"));
		assertEquals(expected, response.body());
	}

	@Deployment(testable = false)
	public static WebArchive createDeployment() {

		JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
				.addAsResource("index.html", "META-INF/resources/index.html");

		return ShrinkWrap.create(WebArchive.class, "ROOT.war")
				.addClass(METAINFResourcesDemo.class)
				.addAsLibrary(jar);
	}
}
