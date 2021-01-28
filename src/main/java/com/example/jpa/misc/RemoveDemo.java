package com.example.jpa.misc;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Future;

import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.OptimisticLockException;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

@Dependent
public class RemoveDemo {

    @Inject
    private EntityManagerFactory emf;
    @Inject
    private UserTransaction utx;
    @Inject
    private ManagedExecutorService executor;

    public void test(Integer id) throws Exception {

        CyclicBarrier cb = new CyclicBarrier(2);

        Future<Void> future = executor.submit(() -> call(cb, id));

        EntityManager em = emf.createEntityManager();
        try {
            utx.begin();

            Foo entity = em.find(Foo.class, id);

            cb.await();

            em.remove(entity);

            try {
                em.flush();
            } catch (OptimisticLockException e) {
                System.out.println(e.getMessage());
            }
        } finally {
            if (utx.getStatus() == Status.STATUS_ACTIVE) {
                utx.commit();
            } else {
                utx.rollback();
            }
            em.close();
        }

        future.get();
    }

    private Void call(CyclicBarrier cb, Integer id) throws Exception {
        EntityManager em = emf.createEntityManager();
        try {
            utx.begin();

            Foo entity = em.find(Foo.class, id);

            cb.await();

            em.remove(entity);

            try {
                em.flush();
            } catch (OptimisticLockException e) {
                System.out.println(e.getMessage());
            }
        } finally {
            em.close();
        }
        return null;
    }
}
