package com.example.cdi.event;

import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

@Dependent
public class EventHandlerImpl {

	private static final Logger logger = Logger.getLogger(EventHandlerImpl.class.getName());

	@Inject
	@Named("list")
	private List<Foo> list;

	public void handle(@Observes Foo foo) {
		logger.info(foo + " handled by " + this);
		list.add(foo);
	}
}
