package com.example.servlet.dispatchertype;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/forward2")
public class ForwardServlet2 extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/forward");
		try (PrintWriter out = resp.getWriter()) {
			out.print("forward2");
		}
		req.getRequestDispatcher("/demo").forward(req, resp);
	}
}
