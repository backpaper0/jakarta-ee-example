package com.example.servlet;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/helloworld")
public class METAINFResourcesDemo extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		ServletContext sc = req.getServletContext();
		try (InputStream in = sc.getResourceAsStream("index.html");
				ServletOutputStream out = resp.getOutputStream()) {
			in.transferTo(out);
			out.flush();
		}
	}
}
