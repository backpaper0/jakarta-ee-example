package com.example.servlet;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/non-blocking-io", asyncSupported = true)
public class NonBlockingIODemo extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/plain");
		AsyncContext ac = req.startAsync();
		ServletOutputStream out = resp.getOutputStream();
		out.setWriteListener(new WriteListener() {

			@Override
			public void onWritePossible() throws IOException {
				out.print("Hello, Non Blocking IO!");
				ac.complete();
			}

			@Override
			public void onError(Throwable t) {
				ac.complete();
				t.printStackTrace();
			}
		});
	}
}
