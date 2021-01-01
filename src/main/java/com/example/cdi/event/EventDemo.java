package com.example.cdi.event;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Dependent
public class EventDemo {

	@Inject
	private Event<Foo> event;

	public void fire(List<Foo> fooList) {
		for (Foo foo : fooList) {
			event.fire(foo);
		}
	}
}
