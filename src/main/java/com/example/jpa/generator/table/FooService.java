package com.example.jpa.generator.table;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@RequestScoped
public class FooService {

    @Inject
    private EntityManager em;

    private static final CyclicBarrier barrier = new CyclicBarrier(2);

    @Transactional
    public List<Foo> create(String tag) {
        Foo entity1 = new Foo();
        entity1.setTag(tag);
        em.persist(entity1);

        em.flush();

        try {
            barrier.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException | BrokenBarrierException | TimeoutException e) {
            throw new RuntimeException(e);
        }

        Foo entity2 = new Foo();
        entity2.setTag(tag);
        em.persist(entity2);

        return List.of(entity1, entity2);
    }
}
