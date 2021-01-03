package com.example.jaxrs.sse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;

@ApplicationScoped
@Path("sse")
public class SSEDemo {

	@Resource(lookup = "java:comp/DefaultManagedScheduledExecutorService")
	private ManagedScheduledExecutorService executor;

	private List<ScheduledFuture<?>> futures;

	@GET
	@Produces(MediaType.SERVER_SENT_EVENTS)
	public void handle(@Context Sse sse, @Context SseEventSink sink) {
		AtomicInteger counter = new AtomicInteger();
		ScheduledFuture<?> future = executor.scheduleAtFixedRate(() -> {
			OutboundSseEvent event = sse.newEventBuilder()
					.mediaType(MediaType.TEXT_PLAIN_TYPE)
					.data(counter.incrementAndGet())
					.build();
			sink.send(event);
		}, 100, 500, TimeUnit.MILLISECONDS);
		futures.add(future);
	}

	@PostConstruct
	public void init() {
		futures = Collections.synchronizedList(new ArrayList<>());
	}

	@PreDestroy
	public void destroy() {
		for (ScheduledFuture<?> future : futures) {
			future.cancel(false);
		}
		futures.clear();
	}
}
