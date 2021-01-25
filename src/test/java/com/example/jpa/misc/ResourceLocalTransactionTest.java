package com.example.jpa.misc;

import static org.junit.Assert.*;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.jpa.JpaProviders;

@RunWith(Arquillian.class)
public class ResourceLocalTransactionTest {

    private static final Logger logger = Logger.getLogger(ResourceLocalTransactionTest.class.getName());

    @Inject
    private ResourceLocalTxDemo demo;
    @Inject
    private EntityManager em;

    @Test
    public void demo() throws Exception {
        demo.execute();
    }

    @Test
    public void withTx() throws Exception {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        logger.info(() -> "Began transaction: " + tx);
        try {
            demo.execute();
            assertTrue(tx.isActive());
            assertFalse(tx.getRollbackOnly());
        } finally {
            if (tx.getRollbackOnly()) {
                tx.rollback();
                logger.info(() -> "Rollbacked transaction: " + tx);
            } else {
                tx.commit();
                logger.info(() -> "Committed transaction: " + tx);
            }
        }
    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap
                .create(JavaArchive.class)
                .addClasses(JpaProviders.class, ResourceLocalTxDemo.class)
                .addAsResource("META-INF/persistence-resourcelocal.xml", "META-INF/persistence.xml");
    }
}
