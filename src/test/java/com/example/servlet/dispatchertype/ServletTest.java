package com.example.servlet.dispatchertype;

import static org.junit.Assert.*;

import java.net.URL;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ServletTest {

	private final Client client = ClientBuilder.newClient();

	@Test
	@RunAsClient
	public void forward1(@ArquillianResource URL url) throws Exception {
		try (Response response = client.target(url.toURI()).path("forward1").request().get()) {
			assertEquals(200, response.getStatus());
			assertEquals("text", response.getMediaType().getType());
			assertEquals("demo", response.getMediaType().getSubtype());
			assertEquals(" demo ", response.readEntity(String.class));
		}
	}

	@Test
	@RunAsClient
	public void include1(@ArquillianResource URL url) throws Exception {
		try (Response response = client.target(url.toURI()).path("include1").request().get()) {
			assertEquals(200, response.getStatus());
			assertEquals("text", response.getMediaType().getType());
			assertEquals("include", response.getMediaType().getSubtype());
			assertEquals("before demo after", response.readEntity(String.class));
		}
	}

	@Deployment(testable = false)
	public static WebArchive createDeployment() {
		return ShrinkWrap
				.create(WebArchive.class)
				.addClasses(DemoServlet.class, ForwardServlet1.class, IncludeServlet1.class);
	}
}
