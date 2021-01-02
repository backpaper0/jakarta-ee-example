package com.example.initialization;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.AfterTypeDiscovery;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;

public class CDIExtensionStartup implements Extension {

	public void handle(@Observes BeforeBeanDiscovery event) {
		Messages.add(BeforeBeanDiscovery.class);
	}

	public void handle(@Observes AfterTypeDiscovery event) {
		Messages.add(AfterTypeDiscovery.class);
	}

	public void handle(@Observes AfterDeploymentValidation event) {
		Messages.add(AfterDeploymentValidation.class);
	}

	public void handle(@Observes AfterBeanDiscovery event) {
		Messages.add(AfterBeanDiscovery.class);
	}
}