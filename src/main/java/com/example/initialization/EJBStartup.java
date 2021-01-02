package com.example.initialization;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class EJBStartup {

	@PostConstruct
	public void init() {
		Messages.add(Startup.class);
	}
}
