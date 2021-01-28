package com.example.servlet;

import java.net.URL;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.jaxrs.JaxrsActivator;

@RunWith(Arquillian.class)
public class ChangeSessionIdDemoTest {

    @Test
    @RunAsClient
    public void test(@ArquillianResource URL url) throws Exception {
        Client client = ClientBuilder.newClient();

        Map<String, NewCookie> cookies;
        try (Response response = client.target(url.toURI()).path("api/demo/init").request().get()) {
            cookies = response.getCookies();
        }

        Invocation.Builder builder = client.target(url.toURI()).path("api/demo/change").request();
        cookies.forEach((k, v) -> builder.cookie(k, v.getValue()));
        String stackTrace = builder.get(String.class);
        System.out.println(stackTrace);
    }

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class)
                .addClasses(JaxrsActivator.class, ChangeSessionIdDemo.class, ChangeSessionIdListener.class);
    }
}
