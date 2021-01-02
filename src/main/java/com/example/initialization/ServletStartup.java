package com.example.initialization;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(loadOnStartup = 1)
public class ServletStartup extends HttpServlet {

	@Override
	public void init() throws ServletException {
		Messages.add(HttpServlet.class);
	}
}
