package com.example.jpa.o2m;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

@Dependent
public class OneToManyDemo {

	@Inject
	private EntityManager em;

	@Inject
	private UserTransaction utx;

	@PostConstruct
	public void init() throws Exception {
		utx.begin();

		Bar bar1 = new Bar(3);
		Bar bar2 = new Bar(4);
		em.merge(bar1);
		em.merge(bar2);

		Foo foo1 = new Foo(1, List.of(bar1, bar2));
		Foo foo2 = new Foo(2, List.of());
		em.merge(foo1);
		em.merge(foo2);

		utx.commit();
	}

	public Foo selectFoo(int fooId) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Foo> q = cb.createQuery(Foo.class);
		Root<Foo> foo = q.from(Foo.class);
		q.select(foo);
		q.where(cb.equal(foo.get(Foo_.id), fooId));
		return em.createQuery(q).getSingleResult();
	}

	public Foo selectFooByBarId(int barId) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Foo> q = cb.createQuery(Foo.class);
		Root<Foo> foo = q.from(Foo.class);
		ListJoin<Foo, Bar> bars = foo.join(Foo_.bars);
		q.select(foo);
		q.where(cb.equal(bars.get(Bar_.id), barId));
		return em.createQuery(q).getSingleResult();
	}
}
