package com.example.cdi.extension;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;

public class ExtensionDemo implements Extension {

	//	public void handle(@Observes BeforeBeanDiscovery event, BeanManager bm) {
	//		System.out.println(event);
	//	}
	//
	//	public void handle(@Observes AfterTypeDiscovery event, BeanManager bm) {
	//		System.out.println(event);
	//	}

	public void handle(@Observes AfterBeanDiscovery event, BeanManager bm) {
		event.addBean()
				.beanClass(Bar.class)
				.types(Bar.class)
				.createWith(ctx -> new Bar());
	}

	//	public void handle(@Observes AfterDeploymentValidation event, BeanManager bm) {
	//		System.out.println(event);
	//	}
	//
	//	public void handle(@Observes BeforeShutdown event, BeanManager bm) {
	//		System.out.println(event);
	//	}

	//	public void handle(@Observes ProcessAnnotatedType<?> event) {
	//	}
	//
	//	public void handle(@Observes ProcessInjectionPoint<?, ?> event) {
	//	}
	//
	//	public void handle(@Observes ProcessInjectionTarget<?> event) {
	//	}
	//
	//	public void handle(@Observes ProcessBeanAttributes<?> event) {
	//	}
	//
	//	public void handle(@Observes ProcessBean<?> event) {
	//	}
	//
	//	public void handle(@Observes ProcessProducer<?, ?> event) {
	//	}
	//
	//	public void handle(@Observes ProcessObserverMethod<?, ?> event) {
	//	}
}
