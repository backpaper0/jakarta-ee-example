package com.example.ra.inbound.demo;

import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;

import org.jboss.ejb3.annotation.ResourceAdapter;

import com.example.ra.inbound.TickListener;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "interval", propertyValue = "1000")
})
@ResourceAdapter("demo.ear#demo-ra.rar")
public class TickDemo implements TickListener {

	private static final Logger logger = Logger.getLogger(TickDemo.class.getName());

	@Override
	public void onTick(int value) {
		logger.info("Tick: " + value);
	}
}
