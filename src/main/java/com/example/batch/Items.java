package com.example.batch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.batch.api.chunk.AbstractItemWriter;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@ApplicationScoped
@Named
public class Items extends AbstractItemWriter {

	private List<Object> items;

	@PostConstruct
	public void init() {
		items = Collections.synchronizedList(new ArrayList<>());
	}

	@Override
	public void writeItems(List<Object> items) throws Exception {
		this.items.addAll(items);
	}

	public List<Object> getItems() {
		return items;
	}

	public void clear() {
		items.clear();
	}
}
