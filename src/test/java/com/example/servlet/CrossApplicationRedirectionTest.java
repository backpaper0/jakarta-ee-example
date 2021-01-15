package com.example.servlet;

import static org.junit.Assert.*;

import java.net.URL;

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
public class CrossApplicationRedirectionTest {

    @Test
    @RunAsClient
    public void crossApplicationRedirection(@ArquillianResource URL url) throws Exception {
        try (Response response = ClientBuilder.newClient().target(url.toURI()).path("foo").request().get()) {
            String body = response.readEntity(String.class);
            assertEquals("hello", body);
        }
    }

    @Deployment(testable = false)
    public static WebArchive createDeployment1() {
        return ShrinkWrap
                .create(WebArchive.class, "app1.war")
                .addClass(CrossApplicationRedirectionServlet1.class);
    }

    @Deployment(testable = false, name = "app2")
    public static WebArchive createDeployment2() {
        return ShrinkWrap
                .create(WebArchive.class, "app2.war")
                .addClass(CrossApplicationRedirectionServlet2.class);
    }
}
