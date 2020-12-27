package com.example.helloworld.batch;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

@Dependent
@Named
public class HelloWorldItemWriter extends AbstractItemWriter {

	@Inject
	@Named("messageQueueForChunk")
	private BlockingQueue<String> messageQueue;

	@Override
	public void writeItems(List<Object> items) throws Exception {
		for (Object item : items) {
			messageQueue.add((String) item);
		}
	}
}
