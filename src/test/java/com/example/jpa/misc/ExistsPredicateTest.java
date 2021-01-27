package com.example.jpa.misc;

import static org.junit.Assert.*;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
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
public class ExistsPredicateTest {

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

		em.merge(new Bar(4, "aaa"));
		em.merge(new Bar(5, "aaa"));
		em.merge(new Bar(6, "aaa"));
		em.merge(new Bar(7, "bbb"));
		em.merge(new Bar(8, "bbb"));
		em.merge(new Bar(9, "ccc"));

		utx.commit();
	}

	@Test
	public void exists() throws Exception {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Bar> q = cb.createQuery(Bar.class);
		Root<Bar> bar = q.from(Bar.class);

		Subquery<Foo> subquery = q.subquery(Foo.class);
		Root<Foo> foo = subquery.from(Foo.class);
		subquery.select(foo);
		subquery.where(cb.equal(foo.get("id"), 2), cb.equal(foo.get("tag"), bar.get("tag")));

		q.where(cb.exists(subquery));

		List<Bar> bars = em.createQuery(q).getResultList();
		assertEquals(List.of(new Bar(7, "bbb"), new Bar(8, "bbb")), bars);
	}

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap
				.create(JavaArchive.class)
				.addClasses(JpaProviders.class, Foo.class, Bar.class)
				.addAsResource("META-INF/persistence.xml");
	}
}
