package com.example.initialization;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.servlet.ServletContext;

@ApplicationScoped
public class CDIInitializedStartup {

	public void init(@Observes @Initialized(ApplicationScoped.class) ServletContext event) {
		Messages.add(Initialized.class);
	}
}
