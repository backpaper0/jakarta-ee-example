package com.example.helloworld.batch;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

@Dependent
public class MessageQueueProvider {

	@ApplicationScoped
	@Named("messageQueueForChunk")
	@Produces
	public BlockingQueue<String> messageQueueForChunk() {
		return new LinkedBlockingDeque<>();
	}

	@ApplicationScoped
	@Named("messageQueueForBatchlet")
	@Produces
	public BlockingQueue<String> messageQueueForBatchlet() {
		return new LinkedBlockingDeque<>();
	}
}
