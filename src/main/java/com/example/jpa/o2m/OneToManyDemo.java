package com.example.jpa.o2m;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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

		Bar bar1 = new Bar(4);
		Bar bar2 = new Bar(5);
		Bar bar3 = new Bar(6);
		em.merge(bar1);
		em.merge(bar2);
		em.merge(bar3);

		Foo foo1 = new Foo(1, List.of(bar1, bar2));
		Foo foo2 = new Foo(2, List.of(bar3));
		Foo foo3 = new Foo(3, List.of());
		em.merge(foo1);
		em.merge(foo2);
		em.merge(foo3);

		utx.commit();
	}

	public Foo selectFoo(int fooId) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Foo> q = cb.createQuery(Foo.class);
		Root<Foo> foo = q.from(Foo.class);
		q.select(foo);
		q.where(cb.equal(foo.get(Foo_.id), fooId));
		q.orderBy(cb.asc(foo.get(Foo_.id)));
		return em.createQuery(q).getSingleResult();
	}
}
