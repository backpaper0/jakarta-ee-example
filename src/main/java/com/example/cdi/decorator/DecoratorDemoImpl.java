package com.example.cdi.decorator;

import javax.enterprise.context.Dependent;

@Dependent
public class DecoratorDemoImpl implements DecoratorDemo {

	@Override
	public String get() {
		return "Hello, Decorator!";
	}
}
