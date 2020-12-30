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
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class FallbackIndexHtmlServletTest {

	private HttpClient client;
	private String indexHtml;

	@Before
	public void init() throws Exception {
		client = HttpClient.newHttpClient();
		indexHtml = Files.readString(Path.of("src/main/resources/index.html"));
	}

	@RunAsClient
	@Test
	public void testRootPath(@ArquillianResource URL resource) throws Exception {
		URI uri = new URL(resource, "/").toURI();
		HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		assertEquals(HttpServletResponse.SC_OK, response.statusCode());
		assertEquals(indexHtml, response.body());
	}

	@RunAsClient
	@Test
	public void testFallback(@ArquillianResource URL resource) throws Exception {
		URI uri = new URL(resource, "/foobar").toURI();
		HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		assertEquals(HttpServletResponse.SC_OK, response.statusCode());
		assertEquals(indexHtml, response.body());
	}

	@RunAsClient
	@Test
	public void testNotFoundAPI(@ArquillianResource URL resource) throws Exception {
		URI uri = new URL(resource, "/api/foobar").toURI();
		HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		assertEquals(HttpServletResponse.SC_NOT_FOUND, response.statusCode());
	}

	@RunAsClient
	@Test
	public void testDirectAccess(@ArquillianResource URL resource) throws Exception {
		URI uri = new URL(resource, "/fallback").toURI();
		HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		assertEquals(HttpServletResponse.SC_OK, response.statusCode());
		assertEquals(indexHtml, response.body());
	}

	@Deployment(testable = false)
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "ROOT.war")
				.add(new ClassLoaderAsset("index.html"), "index.html")
				.addClass(FallbackIndexHtmlServlet.class)
				.setWebXML("fallback-web.xml");
	}
}
