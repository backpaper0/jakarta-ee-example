package com.example.batch.checkpoint;

import java.io.Serializable;
import java.util.PrimitiveIterator;
import java.util.stream.IntStream;

import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.AbstractItemReader;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

@Dependent
@Named
public class BatchCheckpointDemo extends AbstractItemReader {

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
		retry = checkpoint != null;
		iterator = IntStream.range(checkpoint != null ? (Integer) checkpoint : 0, size).iterator();
		value = iterator.hasNext() ? iterator.next() : null;
	}

	@Override
	public Object readItem() throws Exception {
		Integer v = value;
		value = iterator.hasNext() ? iterator.next() : null;
		if (retry == false && v == exceptionIndex) {
			throw new Exception();
		}
		return v;
	}

	@Override
	public Serializable checkpointInfo() throws Exception {
		return value;
	}
}
