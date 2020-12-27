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
		//		jobParameters.setProperty("item-count", "3");
		//		jobParameters.setProperty("size", "10");
		//		jobParameters.setProperty("exception-index", "8");
		long executionId = jobOperator.start("batch-checkpoint-demo", jobParameters);
		Batches.waitForJob(executionId);

		// 例外がスローされたのでFAILD
		assertEquals(BatchStatus.FAILED, jobOperator.getJobExecution(executionId).getBatchStatus());
		List<Object> items = sut.getItems();
		// 失敗するindex = 8なので7まではreadに成功しているが、
		// 8 / 3(item-count) = 2 のため 3 * 2 = 6 しかwriteへ渡っていない。
		assertEquals(6, items.size());
		assertEquals(IntStream.rangeClosed(1, 6).boxed().collect(Collectors.toList()), items);

		executionId = jobOperator.restart(executionId, jobParameters);
		Batches.waitForJob(executionId);

		assertEquals(BatchStatus.COMPLETED,
				jobOperator.getJobExecution(executionId).getBatchStatus());
		assertEquals(10, items.size());
		assertEquals(IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList()), items);
	}

	@Test
	public void testCheckpoint2() throws Exception {
		JobOperator jobOperator = BatchRuntime.getJobOperator();

		Properties jobParameters = new Properties();
		jobParameters.setProperty("item-count", "4");
		//		jobParameters.setProperty("size", "10");
		//		jobParameters.setProperty("exception-index", "8");
		long executionId = jobOperator.start("batch-checkpoint-demo", jobParameters);

		Batches.waitForJob(executionId);

		assertEquals(BatchStatus.FAILED, jobOperator.getJobExecution(executionId).getBatchStatus());
		List<Object> items = sut.getItems();
		assertEquals(4, items.size());
		assertEquals(IntStream.rangeClosed(1, 4).boxed().collect(Collectors.toList()), items);

		executionId = jobOperator.restart(executionId, jobParameters);
		Batches.waitForJob(executionId);

		assertEquals(BatchStatus.COMPLETED,
				jobOperator.getJobExecution(executionId).getBatchStatus());
		assertEquals(10, items.size());
		assertEquals(IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList()), items);
	}

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
				.addClasses(BatchCheckpointDemo.class, Items.class, Batches.class)
				.addAsResource("META-INF/batch-jobs/batch-checkpoint-demo.xml");
	}
}
