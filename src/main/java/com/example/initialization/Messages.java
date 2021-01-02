package com.example.initialization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("messages")
public class Messages {

	private static final List<Class<?>> values = Collections.synchronizedList(new ArrayList<>());

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String get() {
		return values.stream().map(Class::getName).collect(Collectors.joining(","));
	}

	public static void add(Class<?> clazz) {
		values.add(clazz);
	}
}
