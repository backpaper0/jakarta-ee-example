package com.example.batch.skip;

import javax.batch.api.chunk.ItemProcessor;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

@Dependent
@Named
public class SkipDemoItemProcessor implements ItemProcessor {

	@Override
	public Object processItem(Object item) throws Exception {
		if (item instanceof Integer) {
			int value = (int) item;
			if (value % 3 == 2) {
				throw new SkipException();
			}
		}
		return item;
	}
}
