package com.example.ra.inbound;

import javax.resource.ResourceException;
import javax.resource.spi.Activation;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.ConfigProperty;
import javax.resource.spi.InvalidPropertyException;
import javax.resource.spi.ResourceAdapter;

@Activation(messageListeners = TickListener.class)
public class TickActivationSpec implements ActivationSpec {

	// primitiveは受け付けてくれないっぽい（デプロイエラー）。
	// そのためjava.lang.Longで定義している。
	@ConfigProperty
	private Long interval;
	private ResourceAdapter ra;

	public Long getInterval() {
		return interval;
	}

	public void setInterval(Long interval) {
		this.interval = interval;
	}

	@Override
	public ResourceAdapter getResourceAdapter() {
		return ra;
	}

	@Override
	public void setResourceAdapter(ResourceAdapter ra) throws ResourceException {
		this.ra = ra;
	}

	@Override
	public void validate() throws InvalidPropertyException {
	}
}
