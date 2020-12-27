package com.example.helloworld.batch;

import java.util.concurrent.BlockingQueue;

import javax.batch.api.AbstractBatchlet;
import javax.batch.api.BatchProperty;
import javax.batch.runtime.BatchStatus;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

@Dependent
@Named
public class HelloWorldBatchlet extends AbstractBatchlet {

	@Inject
	@BatchProperty(name = "message")
	private String message;

	@Inject
	@Named("messageQueueForBatchlet")
	private BlockingQueue<String> messageQueue;

	@Override
	public String process() throws Exception {
		messageQueue.add(message);
		return BatchStatus.COMPLETED.name();
	}
}
