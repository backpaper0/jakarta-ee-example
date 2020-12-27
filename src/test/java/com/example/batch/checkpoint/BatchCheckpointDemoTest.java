package com.example.batch.checkpoint;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.BatchStatus;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.batch.Batches;
import com.example.batch.Items;

@RunWith(Arquillian.class)
public class BatchCheckpointDemoTest {

	@Inject
	private Items sut;

	@Before
	public void setUp() {
		sut.clear();
	}

	@Test
	public void testCheckpoint1() throws Exception {
		JobOperator jobOperator = BatchRuntime.getJobOperator();

		Properties jobParameters = new Properties();
		jobParameters.setProperty("item-count", "10");
		jobParameters.setProperty("size", "30");
		jobParameters.setProperty("exception-index", "25");
		long executionId = jobOperator.start("batch-checkpoint-demo", jobParameters);
		Batches.waitForJob(executionId);

		assertEquals(BatchStatus.FAILED, jobOperator.getJobExecution(executionId).getBatchStatus());
		List<Object> items = sut.getItems();
		assertEquals(20, items.size());
		assertEquals(IntStream.range(0, 20).boxed().collect(Collectors.toList()), items);

		executionId = jobOperator.restart(executionId, jobParameters);
		Batches.waitForJob(executionId);

		assertEquals(BatchStatus.COMPLETED,
				jobOperator.getJobExecution(executionId).getBatchStatus());
		assertEquals(30, items.size());
		assertEquals(IntStream.range(0, 30).boxed().collect(Collectors.toList()), items);
	}

	@Test
	public void testCheckpoint2() throws Exception {
		JobOperator jobOperator = BatchRuntime.getJobOperator();

		Properties jobParameters = new Properties();
		jobParameters.setProperty("item-count", "12");
		jobParameters.setProperty("size", "30");
		jobParameters.setProperty("exception-index", "25");
		long executionId = jobOperator.start("batch-checkpoint-demo", jobParameters);

		Batches.waitForJob(executionId);

		assertEquals(BatchStatus.FAILED, jobOperator.getJobExecution(executionId).getBatchStatus());
		List<Object> items = sut.getItems();
		assertEquals(24, items.size());
		assertEquals(IntStream.range(0, 24).boxed().collect(Collectors.toList()), items);

		executionId = jobOperator.restart(executionId, jobParameters);
		Batches.waitForJob(executionId);

		assertEquals(BatchStatus.COMPLETED,
				jobOperator.getJobExecution(executionId).getBatchStatus());
		assertEquals(30, items.size());
		assertEquals(IntStream.range(0, 30).boxed().collect(Collectors.toList()), items);
	}

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
				.addClasses(BatchCheckpointDemo.class, Items.class, Batches.class)
				.addAsResource("META-INF/batch-jobs/batch-checkpoint-demo.xml");
	}
}
