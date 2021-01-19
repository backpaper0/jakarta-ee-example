package com.example.jpa.misc;

import static org.junit.Assert.*;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.jpa.JpaProviders;

@RunWith(Arquillian.class)
public class MaxResultsTest {

    @Inject
    private EntityManager em;
    @Inject
    private UserTransaction utx;

    @Before
    public void init() throws Exception {
        utx.begin();

        em.merge(new Foo(1));
        em.merge(new Foo(2));
        em.merge(new Foo(3));

        utx.commit();
    }

    @Test
    public void noMaxResults() throws Exception {
        List<Foo> entities = em.createQuery(query()).getResultList();
        assertEquals(List.of(new Foo(1), new Foo(2), new Foo(3)), entities);
    }

    @Test
    public void maxResults() throws Exception {
        List<Foo> entities = em.createQuery(query()).setMaxResults(1).getResultList();
        assertEquals(List.of(new Foo(1)), entities);
    }

    private CriteriaQuery<Foo> query() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Foo> q = cb.createQuery(Foo.class);
        Root<Foo> foo = q.from(Foo.class);
        return q.select(foo).orderBy(cb.asc(foo.get("id")));
    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap
                .create(JavaArchive.class)
                .addClasses(JpaProviders.class, Foo.class)
                .addAsResource("META-INF/persistence.xml");
    }
}
