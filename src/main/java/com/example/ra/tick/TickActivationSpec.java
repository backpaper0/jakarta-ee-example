package com.example.ra.tick;

import javax.resource.ResourceException;
import javax.resource.spi.Activation;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.InvalidPropertyException;
import javax.resource.spi.ResourceAdapter;

@Activation(messageListeners = TickListener.class)
public class TickActivationSpec implements ActivationSpec {

	private ResourceAdapter ra;

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
