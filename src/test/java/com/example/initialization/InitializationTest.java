package com.example.initialization;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Startup;
import javax.enterprise.context.Initialized;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.AfterTypeDiscovery;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * <a href="http://backpaper0.github.io/2015/12/24/run_at_startup_in_javaee.html">
 * Java EEアプリケーションで起動時になにかしらの処理をする方法
 * </a>
 *
 */
@RunWith(Arquillian.class)
public class InitializationTest {

	@RunAsClient
	@Test
	public void testInit(@ArquillianResource URL resource) throws Exception {
		URI uri = new URL(resource, "api/messages").toURI();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

		Set<String> actual = Arrays.stream(response.body().split(","))
				.collect(Collectors.toSet());

		Set<String> expected = Stream.of(BeforeBeanDiscovery.class,
				AfterTypeDiscovery.class,
				AfterDeploymentValidation.class,
				AfterBeanDiscovery.class,
				Initialized.class,
				Startup.class,
				ServletContainerInitializer.class,
				ServletContextListener.class,
				HttpServlet.class).map(Class::getName).collect(Collectors.toSet());

		assertEquals(expected, actual);
	}

	@Deployment(testable = false)
	public static WebArchive createDeployment() {

		JavaArchive cdiExtensionJar = ShrinkWrap.create(JavaArchive.class)
				.addClass(CDIExtensionStartup.class)
				.addAsResource(new StringAsset(CDIExtensionStartup.class.getName()),
						"META-INF/services/" + Extension.class.getName());

		JavaArchive sciJar = ShrinkWrap.create(JavaArchive.class)
				.addClass(ServletContainerInitializerStartup.class)
				.addAsResource(new StringAsset(ServletContainerInitializerStartup.class.getName()),
						"META-INF/services/" + ServletContainerInitializer.class.getName());

		return ShrinkWrap.create(WebArchive.class)
				.addClasses(CDIInitializedStartup.class, EJBStartup.class, JaxrsActivator.class,
						Messages.class, ServletContextListenerStartup.class, ServletStartup.class)
				.addAsLibraries(cdiExtensionJar, sciJar);
	}
}
