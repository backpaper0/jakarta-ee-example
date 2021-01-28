package com.example.concurrency;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;

@Dependent
public class ConcurrencyUtilityProviders {

    //    @Resource(lookup = "java:comp/DefaultManagedExecutorService")
    //    private ManagedExecutorService executor;

    @Resource(lookup = "java:comp/DefaultManagedScheduledExecutorService")
    private ManagedScheduledExecutorService scheduler;

    //    @Produces
    //    public ManagedExecutorService managedExecutorService() {
    //        return executor;
    //    }

    @Produces
    public ManagedScheduledExecutorService managedScheduledExecutorService() {
        return scheduler;
    }
}
