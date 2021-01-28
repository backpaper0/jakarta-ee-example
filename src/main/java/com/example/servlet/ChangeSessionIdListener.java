package com.example.servlet;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;

@ApplicationScoped
@WebListener
public class ChangeSessionIdListener implements HttpSessionIdListener {

    @Inject
    private ChangeSessionIdDemo demo;

    @Override
    public void sessionIdChanged(HttpSessionEvent event, String oldSessionId) {
        StringWriter sw = new StringWriter();
        PrintWriter out = new PrintWriter(sw);
        String message = String.format("Session ID changed: from %s to %s", oldSessionId, event.getSession().getId());
        Throwable t = new Throwable(message);
        t.printStackTrace(out);
        out.flush();
        out.close();
        demo.setStackTrace(sw.toString());
    }
}
