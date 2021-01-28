package com.example.jpa;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

@Dependent
public class JpaProviders {

    @PersistenceUnit(unitName = "demo")
    private EntityManagerFactory emf;

    @PersistenceContext(unitName = "demo")
    private EntityManager em;

    @Produces
    public EntityManagerFactory entityManagerFactory() {
        return emf;
    }

    @Produces
    public EntityManager entityManager() {
        return em;
    }
}
