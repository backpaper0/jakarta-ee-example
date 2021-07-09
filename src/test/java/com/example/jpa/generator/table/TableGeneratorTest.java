package com.example.jpa.generator.table;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URL;
import java.util.concurrent.Future;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.jaxrs.JaxrsActivator;
import com.example.jpa.JpaProviders;

@RunWith(Arquillian.class)
public class TableGeneratorTest {

    private static final Logger logger = LoggerFactory.getLogger(TableGeneratorTest.class);

    @Test
    @RunAsClient
    public void test(@ArquillianResource URL url) throws Exception {
        URI uri = new URL(url, "api/foo").toURI();
        Client client = ClientBuilder.newClient();

        Future<Response> future1 = client.target(uri).request().async().post(Entity.text("aaa"));
        Future<Response> future2 = client.target(uri).request().async().post(Entity.text("bbb"));

        try (Response response = future1.get()) {
            String id = response.readEntity(String.class);
            logger.info("{}", id);
            assertNotNull(id);
        }
        try (Response response = future2.get()) {
            String id = response.readEntity(String.class);
            logger.info("{}", id);
            assertNotNull(id);
        }
    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap
                .create(JavaArchive.class)
                .addClasses(JpaProviders.class, Foo.class)
                .addClasses(JaxrsActivator.class, FooResource.class)
                .addClasses(FooService.class)
                .addAsResource("META-INF/persistence.xml");
    }
}
