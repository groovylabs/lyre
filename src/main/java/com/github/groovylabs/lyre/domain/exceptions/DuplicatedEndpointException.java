package com.github.groovylabs.lyre.domain.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class DuplicatedEndpointException extends WebApplicationException {

    public DuplicatedEndpointException(String message) {
        super(Response.status(Response.Status.CONFLICT).entity(message).type(MediaType.TEXT_PLAIN).build());
    }
}
