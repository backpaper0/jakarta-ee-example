package com.example.jaxrs.sse;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;

@ApplicationScoped
@Path("sse")
public class SSEBroadcastDemo {

	@Context
	private Sse sse;

	private SseBroadcaster broadcaster;

	@GET
	@Path("register")
	@Produces(MediaType.SERVER_SENT_EVENTS)
	public void register(@Context SseEventSink sink) {
		broadcaster.register(sink);
	}

	@POST
	@Path("post")
	@Consumes(MediaType.TEXT_PLAIN)
	public void post(String data) {
		OutboundSseEvent event = sse.newEventBuilder()
				.mediaType(MediaType.TEXT_PLAIN_TYPE)
				.data(data)
				.build();
		broadcaster.broadcast(event);
	}

	@PostConstruct
	public void init() {
		broadcaster = sse.newBroadcaster();
		broadcaster.onClose(sink -> {
			System.out.println("Closed: " + sink);
		});
	}

	@PreDestroy
	public void destroy() {
		broadcaster.close();
	}
}
