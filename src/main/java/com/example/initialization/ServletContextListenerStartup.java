package com.example.initialization;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletContextListenerStartup implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Messages.add(ServletContextListener.class);
	}
}
