package com.example.cdi.extension;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Foo {

	public String get() {
		return "foo";
	}
}
