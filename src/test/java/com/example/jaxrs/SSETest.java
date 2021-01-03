package com.example.jaxrs;

import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.sse.SseEventSource;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.jaxrs.sse.SSEDemo;

@RunWith(Arquillian.class)
public class SSETest {

	@RunAsClient
	@Test
	public void testSSE(@ArquillianResource URL url) throws Exception {
		URI uri = new URL(url, "api/sse").toURI();
		Client client = ClientBuilder.newClient();
		WebTarget endpoint = client.target(uri);
		try (SseEventSource source = SseEventSource.target(endpoint).build()) {
			source.register(event -> {
				System.out.printf("[%s] %s%n", LocalDateTime.now(), event.readData());
			});
			source.open();
			TimeUnit.SECONDS.sleep(3);
		}
	}

	@Deployment(testable = false)
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class)
				.addClasses(JaxrsActivator.class, SSEDemo.class);
	}
}
