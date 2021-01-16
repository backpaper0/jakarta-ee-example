package com.example.servlet.filterdispatchertype;

import static org.junit.Assert.*;

import java.net.URL;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class FilterDispatcherTypeTest {

	private final Client client = ClientBuilder.newClient();

	@Test
	@RunAsClient
	public void demo(@ArquillianResource URL url) throws Exception {
		try (var response = client.target(url.toURI()).path("demo").request().get()) {
			String body = response.readEntity(String.class);
			assertEquals("Foo Qux Demo", body);
		}
	}

	@Test
	@RunAsClient
	public void forward(@ArquillianResource URL url) throws Exception {
		try (var response = client.target(url.toURI()).path("forward").request().get()) {
			String body = response.readEntity(String.class);
			assertEquals("Foo Qux Forward Bar Qux Demo", body);
		}
	}

	@Test
	@RunAsClient
	public void error(@ArquillianResource URL url) throws Exception {
		try (var response = client.target(url.toURI()).path("error").request().get()) {
			String body = response.readEntity(String.class);
			assertEquals("Foo Qux Error Baz Qux Demo", body);
		}
	}

	@Deployment(testable = false)
	public static WebArchive createDeployment() {
		return ShrinkWrap
				.create(WebArchive.class)
				.addClasses(DemoServlet.class, DemoFilter.class)
				.setWebXML("filter-dispatchertype-web.xml");
	}
}
