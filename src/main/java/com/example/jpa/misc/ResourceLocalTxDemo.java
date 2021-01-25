package com.example.jpa.misc;

import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@Dependent
public class ResourceLocalTxDemo {

    private static final Logger logger = Logger.getLogger(ResourceLocalTxDemo.class.getName());

    @Inject
    private EntityManager em;

    public void execute() {
        EntityTransaction tx = em.getTransaction();
        boolean began = false;
        if (tx.isActive() == false) {
            tx.begin();
            began = true;
            logger.info(() -> "Began transaction: " + tx);
        }
        try {
            logger.info(() -> "hello world");
        } catch (Throwable t) {
            tx.setRollbackOnly();
            throw t;
        } finally {
            if (began) {
                if (tx.getRollbackOnly()) {
                    tx.rollback();
                    logger.info(() -> "Rollbacked transaction: " + tx);
                } else {
                    tx.commit();
                    logger.info(() -> "Committed transaction: " + tx);
                }
            }
        }
    }
}
