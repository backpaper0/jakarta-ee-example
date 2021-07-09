package com.example.jpa.generator.table;

import java.util.Objects;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RequestScoped
@Path("/foo")
public class FooResource {

    @Inject
    private FooService service;

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String post(String tag) {
        return service
                .create(tag)
                .stream()
                .map(Foo::getId)
                .map(Objects::toString)
                .collect(Collectors.joining(", "));
    }
}
