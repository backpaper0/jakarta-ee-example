package com.example.ra.tick.demo;

import java.util.logging.Logger;

import javax.ejb.MessageDriven;
import javax.enterprise.context.Dependent;

import org.jboss.ejb3.annotation.ResourceAdapter;

import com.example.ra.tick.TickListener;

@Dependent
@MessageDriven
@ResourceAdapter("demo.ear#demo-ra.rar")
public class TickDemo implements TickListener {

	private static final Logger logger = Logger.getLogger(TickDemo.class.getName());

	@Override
	public void onTick(int value) {
		logger.info("Tick: " + value);
	}
}
