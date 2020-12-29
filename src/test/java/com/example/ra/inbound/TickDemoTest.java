package com.example.ra.inbound;

import java.util.concurrent.TimeUnit;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.ResourceAdapterArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.ra.inbound.demo.TickDemo;

@RunWith(Arquillian.class)
public class TickDemoTest {

	@RunAsClient
	@Test
	public void testResourceAdapter() throws Exception {
		// 受け取った値をログ出力しているのでコンソールを確認すること
		TimeUnit.SECONDS.sleep(3);
	}

	@Deployment(testable = false)
	public static EnterpriseArchive createDeployment() {

		// RAR
		JavaArchive raJar = ShrinkWrap.create(JavaArchive.class)
				.addClasses(TickActivation.class, TickActivationSpec.class, TickListener.class,
						TickResourceAdapter.class);
		ResourceAdapterArchive rar = ShrinkWrap.create(ResourceAdapterArchive.class, "demo-ra.rar")
				.addAsLibrary(raJar);

		// 動作確認用のクラス入りJAR
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "demo.jar")
				.addClasses(TickDemo.class);

		EnterpriseArchive ear = ShrinkWrap.create(EnterpriseArchive.class, "demo.ear")
				.addAsModules(rar, jar)
				.addAsApplicationResource("com/example/ra/application.xml", "application.xml");
		return ear;
	}
}
