package com.example.cdi.event;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

@Dependent
public class FooHandler {

	@Inject
	@Named("list")
	private List<Foo> list;

	public void handle(@Observes Foo foo) {
		list.add(foo);
	}
}
