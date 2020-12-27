package com.example.batch.checkpoint;

import java.io.Serializable;
import java.util.PrimitiveIterator;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.AbstractItemReader;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

@Dependent
@Named
public class BatchCheckpointDemo extends AbstractItemReader {

	private static final Logger logger = Logger.getLogger(BatchCheckpointDemo.class.getName());

	private PrimitiveIterator.OfInt iterator;
	private Integer value;
	private boolean retry;

	@Inject
	@BatchProperty(name = "size")
	private int size;
	@Inject
	@BatchProperty(name = "exception-index")
	private int exceptionIndex;

	@Override
	public void open(Serializable checkpoint) throws Exception {
		logger.info("open: " + checkpoint);
		retry = checkpoint != null;
		iterator = IntStream.rangeClosed(checkpoint != null ? (Integer) checkpoint : 1, size)
				.iterator();
		value = iterator.hasNext() ? iterator.next() : null;
	}

	@Override
	public Object readItem() throws Exception {
		logger.info("readItem: " + value);
		Integer v = value;
		value = iterator.hasNext() ? iterator.next() : null;
		if (retry == false && v == exceptionIndex) {
			throw new Exception();
		}
		return v;
	}

	@Override
	public Serializable checkpointInfo() throws Exception {
		logger.info("checkpointInfo: " + value);
		return value;
	}
}
