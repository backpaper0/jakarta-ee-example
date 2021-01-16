package com.example.servlet.filterdispatchertype;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DemoServlet extends HttpServlet {

	private String name;

	@Override
	public void init(ServletConfig config) throws ServletException {
		name = config.getInitParameter("name");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		List<String> names = (List<String>) req.getAttribute("names");
		if (names == null) {
			names = new ArrayList<>();
			req.setAttribute("names", names);
		}

		names.add(name);

		if (name.equalsIgnoreCase("Forward")) {
			req.getRequestDispatcher("/demo").forward(req, resp);
			return;
		}

		if (name.equalsIgnoreCase("Error")) {
			throw new RuntimeException();
		}

		if (name.equalsIgnoreCase("JSP")) {
			req.getRequestDispatcher("/WEB-INF/demo.jsp").forward(req, resp);
			return;
		}

		resp.setContentType("text/demo");
		try (PrintWriter out = resp.getWriter()) {
			out.print(names.stream().collect(Collectors.joining(" ")));
		}
	}
}
