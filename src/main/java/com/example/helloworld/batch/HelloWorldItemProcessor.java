package com.example.helloworld.batch;

import javax.batch.api.chunk.ItemProcessor;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

@Dependent
@Named
public class HelloWorldItemProcessor implements ItemProcessor {

	@Override
	public Object processItem(Object item) throws Exception {
		return "*" + item + "*";
	}
}
