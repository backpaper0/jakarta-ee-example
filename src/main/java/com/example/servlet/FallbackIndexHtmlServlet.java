package com.example.servlet;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 404 Not Foundの場合にindex.htmlを返すようフォールバックするサーブレット。
 * SPAで静的リソース(index.html, *.js, *.css)もWARにパッケージするケースでの利用を想定している。
 * 404 Not Foundの場合にこのサーブレットへディスパッチする設定はweb.xmlで行っている。
 *
 */
@WebServlet(name = "FallbackIndexHtmlServlet", urlPatterns = "/fallback")
public class FallbackIndexHtmlServlet extends HttpServlet {

	@Override
	protected void service(final HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {

		if (req.getDispatcherType().equals(DispatcherType.ERROR) == false) {
			// 直接 /fallback へアクセスされた場合は index.html を返す
			req.getRequestDispatcher("index.html").forward(req, resp);
			return;
		}

		final String requestUri = (String) req.getAttribute("javax.servlet.error.request_uri");

		// 存在しないAPIへのリクエストは404を返す
		if (requestUri.startsWith("/api/")) {
			int errorStatusCode = (int) req.getAttribute("javax.servlet.error.status_code");
			String errorMessage = (String) req.getAttribute("javax.servlet.error.message");
			resp.sendError(errorStatusCode, errorMessage);
			return;
		}

		resp.setStatus(HttpServletResponse.SC_OK);
		req.getRequestDispatcher("index.html").forward(req, resp);
	}
}