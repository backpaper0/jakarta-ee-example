package com.example.jpa;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Dependent
public class JpaProviders {

	@PersistenceContext(unitName = "demo")
	private EntityManager em;

	@Produces
	public EntityManager entityManager() {
		return em;
	}
}
