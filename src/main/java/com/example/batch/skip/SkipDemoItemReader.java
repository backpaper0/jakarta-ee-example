package com.example.batch.skip;

import java.io.Serializable;
import java.util.PrimitiveIterator;
import java.util.stream.IntStream;

import javax.batch.api.chunk.AbstractItemReader;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

@Dependent
@Named
public class SkipDemoItemReader extends AbstractItemReader {

	private PrimitiveIterator.OfInt iterator;

	@Override
	public void open(Serializable checkpoint) throws Exception {
		iterator = IntStream.range(0, 20).iterator();
	}

	@Override
	public Object readItem() throws Exception {
		if (iterator.hasNext() == false) {
			return null;
		}
		int value = iterator.nextInt();
		if (value % 3 == 1) {
			throw new SkipException();
		}
		return value;
	}
}
