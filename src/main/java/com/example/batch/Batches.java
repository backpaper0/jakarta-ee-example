package com.example.batch;

import java.util.concurrent.TimeUnit;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;

public class Batches {

	public static void waitForJob(long executionId) {
		JobOperator jobOperator = BatchRuntime.getJobOperator();
		JobExecution jobExecution = jobOperator.getJobExecution(executionId);
		while (jobExecution.getEndTime() == null) {
			try {
				TimeUnit.MILLISECONDS.sleep(10);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				return;
			}
		}
	}
}
