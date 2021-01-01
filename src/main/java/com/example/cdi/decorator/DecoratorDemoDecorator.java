package com.example.cdi.decorator;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

@Decorator
public class DecoratorDemoDecorator implements DecoratorDemo {

	@Inject
	@Delegate
	private DecoratorDemo delegate;

	@Override
	public String get() {
		return delegate.get() + "!!";
	}
}
