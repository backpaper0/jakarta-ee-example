package com.example.helloworld.batch;

import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.AbstractItemReader;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

@Dependent
@Named
public class HelloWorldItemReader extends AbstractItemReader {

	@Inject
	@BatchProperty(name = "message")
	private String message;

	@Inject
	@BatchProperty(name = "size")
	private int size;

	private int count;

	@Override
	public Object readItem() throws Exception {
		if (count < size) {
			count++;
			return message;
		}
		return null;
	}
}
