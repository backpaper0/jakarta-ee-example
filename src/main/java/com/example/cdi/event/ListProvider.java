package com.example.cdi.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

@Dependent
public class ListProvider {

	@Produces
	@ApplicationScoped
	@Named("list")
	public List<Foo> list() {
		return Collections.synchronizedList(new ArrayList<>());
	}
}
