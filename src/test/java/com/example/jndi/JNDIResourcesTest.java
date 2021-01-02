package com.example.jndi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class JNDIResourcesTest {

	@Test
	public void testJavaComp() throws Exception {
		new Output().traverse("java:comp");
		// java:comp/DefaultContextService :: java.lang.Object
		// java:comp/DefaultDataSource :: java.lang.Object
		// java:comp/DefaultJMSConnectionFactory :: java.lang.Object
		// java:comp/DefaultManagedExecutorService :: java.lang.Object
		// java:comp/DefaultManagedScheduledExecutorService :: java.lang.Object
		// java:comp/DefaultManagedThreadFactory :: java.lang.Object
		// java:comp/HandleDelegate :: org.jboss.as.ejb3.iiop.handle.HandleDelegateImpl
		// java:comp/InAppClientContainer :: java.lang.Boolean
		// java:comp/InstanceName :: java.lang.Object
		// java:comp/ModuleName :: java.lang.String
		// java:comp/ORB :: com.sun.corba.se.impl.orb.ORBImpl
		// java:comp/TransactionSynchronizationRegistry :: org.jboss.as.txn.service.internal.tsr.TransactionSynchronizationRegistryWrapper
		// java:comp/UserTransaction :: javax.transaction.UserTransaction
		// java:comp/Validator :: javax.validation.Validator
		// java:comp/ValidatorFactory :: org.jboss.as.ee.beanvalidation.LazyValidatorFactory
		// java:comp/env :: org.jboss.as.naming.NamingContext
	}

	@Test
	public void testJavaJboss() throws Exception {
		new Output().traverse("java:jboss");
		// java:jboss/ee/concurrency/scheduler/default :: java.lang.Object
		// java:jboss/ee/concurrency/factory/default :: java.lang.Object
		// java:jboss/ee/concurrency/executor/default :: java.lang.Object
		// java:jboss/ee/concurrency/context/default :: java.lang.Object
		// java:jboss/ee/concurrency/context :: javax.naming.Context
		// java:jboss/ee/concurrency/executor :: javax.naming.Context
		// java:jboss/ee/concurrency/factory :: javax.naming.Context
		// java:jboss/ee/concurrency/scheduler :: javax.naming.Context
		// java:jboss/ee/concurrency :: javax.naming.Context
		// java:jboss/infinispan/container/ejb :: org.jboss.as.clustering.infinispan.DefaultCacheContainer
		// java:jboss/infinispan/configuration/ejb/default :: org.infinispan.configuration.cache.Configuration
		// java:jboss/infinispan/configuration/ejb/passivation :: org.infinispan.configuration.cache.Configuration
		// java:jboss/infinispan/configuration/ejb :: javax.naming.Context
		// java:jboss/infinispan/configuration :: javax.naming.Context
		// java:jboss/infinispan/container :: javax.naming.Context
		// java:jboss/datasources/ExampleDS :: org.jboss.as.connector.subsystems.datasources.WildFlyDataSource
		// java:jboss/mail/Default :: javax.mail.Session
		// java:jboss/clustering/registry/ejb/http-remoting-connector :: org.wildfly.clustering.server.registry.FunctionalRegistryFactory
		// java:jboss/clustering/registry/ejb :: javax.naming.Context
		// java:jboss/clustering/group/default :: org.wildfly.clustering.server.group.LocalGroup
		// java:jboss/clustering/group/ejb :: org.wildfly.clustering.server.group.LocalGroup
		// java:jboss/clustering/group/hibernate :: org.wildfly.clustering.server.group.LocalGroup
		// java:jboss/clustering/group/local :: org.wildfly.clustering.server.group.LocalGroup
		// java:jboss/clustering/group/server :: org.wildfly.clustering.server.group.LocalGroup
		// java:jboss/clustering/group/web :: org.wildfly.clustering.server.group.LocalGroup
		// java:jboss/clustering/group :: javax.naming.Context
		// java:jboss/clustering/registry :: javax.naming.Context
		// java:jboss/ORB :: com.sun.corba.se.impl.orb.ORBImpl
		// java:jboss/TransactionManager :: org.wildfly.transaction.client.ContextTransactionManager
		// java:jboss/TransactionSynchronizationRegistry :: org.jboss.as.txn.service.internal.tsr.TransactionSynchronizationRegistryWrapper
		// java:jboss/UserTransaction :: javax.transaction.UserTransaction
		// java:jboss/clustering :: javax.naming.Context
		// java:jboss/corbanaming :: org.omg.CosNaming._NamingContextExtStub
		// java:jboss/datasources :: javax.naming.Context
		// java:jboss/ee :: javax.naming.Context
		// java:jboss/infinispan :: javax.naming.Context
		// java:jboss/irpoa :: com.sun.corba.se.impl.oa.poa.POAImpl
		// java:jboss/jaas :: com.sun.proxy.$Proxy1057
		// java:jboss/mail :: javax.naming.Context
		// java:jboss/poa :: com.sun.corba.se.impl.oa.poa.POAImpl
	}

	private static class Output {

		private final Context ic;

		public Output() throws NamingException {
			this.ic = new InitialContext();
		}

		public void traverse(String prefix) throws NamingException {
			List<String> list = new ArrayList<>();
			traverse(prefix, list);
			Collections.sort(list);
			list.forEach(System.out::println);
		}

		private void traverse(String prefix, List<String> list) throws NamingException {
			NamingEnumeration<NameClassPair> ne = ic.list(prefix);
			while (ne.hasMore()) {
				NameClassPair nameClass = ne.next();
				String name = prefix + "/" + nameClass.getName();
				list.add(name + " :: " + nameClass.getClassName());
				if (nameClass.getClassName().equals(Context.class.getName())) {
					traverse(name);
				}
			}
		}
	}

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class);
	}
}
