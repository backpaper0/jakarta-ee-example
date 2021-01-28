package com.example.servlet;

import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@RequestScoped
@Path("demo")
public class ChangeSessionIdDemo {

    @Context
    private HttpServletRequest request;

    private String stackTrace;

    @GET
    @Path("init")
    public void init() {
        request.getSession();
    }

    @GET
    @Path("change")
    @Produces(MediaType.TEXT_PLAIN)
    public String change() {
        request.changeSessionId();
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }
}
