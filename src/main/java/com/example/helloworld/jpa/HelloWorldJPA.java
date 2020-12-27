package com.example.helloworld.jpa;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Dependent
@Transactional
public class HelloWorldJPA {

	@PersistenceContext(unitName = "demo")
	private EntityManager em;

	public Demo create(String message) {
		Demo entity = new Demo();
		entity.setMessage(message);
		em.persist(entity);
		return entity;
	}

	public void update(Long id, String message) {
		Demo entity = em.find(Demo.class, id);
		entity.setMessage(message);
	}

	public void remove(Long id) {
		Demo entity = em.find(Demo.class, id);
		em.remove(entity);
	}

	public Demo find(Long id) {
		return em.find(Demo.class, id);
	}
}
