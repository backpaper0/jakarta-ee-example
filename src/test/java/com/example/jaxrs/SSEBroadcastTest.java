package com.example.jaxrs;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.sse.SseEventSource;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.jaxrs.sse.SSEBroadcastDemo;

@RunWith(Arquillian.class)
public class SSEBroadcastTest {

	@RunAsClient
	@Test
	public void testBroadcast(@ArquillianResource URL url) throws Exception {
		URI uri = new URL(url, "api/sse").toURI();
		Client client = ClientBuilder.newClient();
		WebTarget endpoint = client.target(uri).path("register");

		List<SseEventSource> sources = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			SseEventSource source = SseEventSource.target(endpoint).build();
			sources.add(source);
			int index = i;
			source.register(event -> {
				System.out.printf("[%s] %s%n", index, event.readData());
			});
			source.open();
		}

		for (String data : "foo bar baz".split(" ")) {
			Entity<String> entity = Entity.text(data);
			Response response = client.target(uri).path("post").request().post(entity);
			response.close();
		}

		TimeUnit.SECONDS.sleep(1);

		for (SseEventSource source : sources) {
			source.close();
		}
	}

	@Deployment(testable = false)
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class)
				.addClasses(JaxrsActivator.class, SSEBroadcastDemo.class);
	}
}
