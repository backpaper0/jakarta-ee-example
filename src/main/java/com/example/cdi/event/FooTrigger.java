package com.example.cdi.event;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Dependent
public class FooTrigger {

	@Inject
	private Event<Foo> event;

	public void fire(Foo foo) {
		event.fire(foo);
	}
}
