package com.example.jpa.misc;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.concurrency.ConcurrencyUtilityProviders;
import com.example.jpa.JpaProviders;

@RunWith(Arquillian.class)
public class RemoveDemoTest {

    @Inject
    private RemoveDemo demo;

    @Inject
    private EntityManager em;
    @Inject
    private UserTransaction utx;

    @Before
    public void init() throws Exception {
        utx.begin();

        em.merge(new Foo(1, "aaa"));
        em.merge(new Foo(2, "bbb"));
        em.merge(new Foo(3, "ccc"));

        utx.commit();
    }

    @Test
    public void test() throws Exception {
        demo.test(2);
    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap
                .create(JavaArchive.class)
                .addClasses(JpaProviders.class, ConcurrencyUtilityProviders.class, RemoveDemo.class, Foo.class)
                .addAsResource("META-INF/persistence.xml");
    }
}
