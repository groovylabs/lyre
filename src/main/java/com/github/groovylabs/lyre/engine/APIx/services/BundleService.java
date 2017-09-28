package com.github.groovylabs.lyre.engine.APIx.services;

import com.github.groovylabs.lyre.domain.Bundle;
import com.github.groovylabs.lyre.domain.Endpoint;
import com.github.groovylabs.lyre.domain.exceptions.DuplicatedEndpointException;
import com.github.groovylabs.lyre.domain.exceptions.EndpointNotFoundException;
import com.github.groovylabs.lyre.engine.APIx.APIx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path(value = "/bundle")
public class BundleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BundleService.class);

    @Autowired
    private Bundle bundle;

    @Autowired
    private APIx apix;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response message() {
        return Response.ok().entity(bundle).build();
    }

    /**
     * Responsable to create one {@link com.github.groovylabs.lyre.domain.Endpoint} inside the bundle object.
     * If already exists this Endpoint in the Bundle, this method will return 409 HTTP STATUS (CONFLICT).
     *
     * @return Response
     */
    @PUT
    public Response add(Endpoint endpoint) {
        LOGGER.info("Adding endpoint in bundle list...");

        if (bundle.exists(endpoint)) {
            LOGGER.info("Endpoint already exists in bundle list.");
            throw new DuplicatedEndpointException("Endpoint [" + endpoint.getMethod() + " - " + endpoint.getPath() + "] already exists!");
        }
        else {
            bundle.add(endpoint);
            LOGGER.info("Endpoint added. Creating the endpoint in Jersey.");
            apix.boot();
        }

        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(Endpoint endpoint) {
        LOGGER.info("Updating endpoint...");

        bundle.change(endpoint);
        apix.boot();

        return Response.ok().entity(bundle).build();
    }

}
