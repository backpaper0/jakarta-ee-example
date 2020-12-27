package com.example.helloworld.batch;

import static org.junit.Assert.*;

import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class HelloWorldChunkTest {

	@Inject
	@Named("messageQueueForChunk")
	private BlockingQueue<String> sut;

	@Test
	public void testBatchlet() throws Exception {
		JobOperator jobOperator = BatchRuntime.getJobOperator();
		Properties props = new Properties();
		jobOperator.start("helloworld-chunk", props);

		assertEquals("*Hello, Chunk!*", sut.poll(10, TimeUnit.SECONDS));
		assertEquals("*Hello, Chunk!*", sut.poll(10, TimeUnit.SECONDS));
		assertEquals("*Hello, Chunk!*", sut.poll(10, TimeUnit.SECONDS));
		assertNull(sut.poll());
	}

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
				.addClasses(HelloWorldItemReader.class, HelloWorldItemProcessor.class,
						HelloWorldItemWriter.class, MessageQueueProvider.class)
				.addAsResource("META-INF/batch-jobs/helloworld-chunk.xml");
	}
}
