package com.example.ra.inbound;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.Connector;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.transaction.xa.XAResource;

@Connector
public class TickResourceAdapter implements ResourceAdapter {

	private BootstrapContext ctx;
	private Map<ActivationSpec, TickActivation> activations;

	@Override
	public void start(BootstrapContext ctx) throws ResourceAdapterInternalException {
		this.ctx = ctx;
		this.activations = Collections.synchronizedMap(new HashMap<>());
	}

	@Override
	public void stop() {
	}

	@Override
	public void endpointActivation(MessageEndpointFactory endpointFactory, ActivationSpec spec)
			throws ResourceException {
		TickActivation activation = new TickActivation();
		TickListener listener = (TickListener) endpointFactory.createEndpoint(activation);
		activations.put(spec, activation);
		activation.start(ctx, listener);
	}

	@Override
	public void endpointDeactivation(MessageEndpointFactory endpointFactory, ActivationSpec spec) {
		TickActivation activation = activations.get(spec);
		if (activation != null) {
			activation.stop();
		}
	}

	@Override
	public XAResource[] getXAResources(ActivationSpec[] specs) throws ResourceException {
		return null;
	}

	@Override
	public int hashCode() {
		return System.identityHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return obj == this;
	}
}
