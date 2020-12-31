package com.example.batch.skip;

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
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.batch.Batches;
import com.example.batch.Items;

@RunWith(Arquillian.class)
public class SkipDemoTest {

	@Inject
	private Items sut;

	@Test
	public void testSkip() throws Exception {
		JobOperator jobOperator = BatchRuntime.getJobOperator();

		Properties jobParameters = new Properties();
		long executionId = jobOperator.start("skip-demo", jobParameters);
		Batches.waitForJob(executionId);

		assertEquals(BatchStatus.COMPLETED,
				jobOperator.getJobExecution(executionId).getBatchStatus());

		List<Integer> expected = IntStream.range(0, 20).filter(a -> a % 3 == 0).boxed()
				.collect(Collectors.toList());
		assertEquals(expected, sut.getItems());
	}

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
				.addClasses(SkipDemoItemReader.class, SkipDemoItemProcessor.class,
						Items.class, SkipException.class, Batches.class)
				.addAsResource("META-INF/batch-jobs/skip-demo.xml");
	}
}
