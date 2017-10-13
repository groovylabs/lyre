/*
 * MIT License
 *
 * Copyright (c) 2017 Groovylabs
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.github.groovylabs.lyre.engine.APIx.services;

import com.github.groovylabs.lyre.domain.Bundle;
import com.github.groovylabs.lyre.domain.Endpoint;
import com.github.groovylabs.lyre.domain.exceptions.DuplicatedEndpointException;
import com.github.groovylabs.lyre.engine.APIx.controller.APIxController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path(value = "/bundle")
@Produces(MediaType.APPLICATION_JSON)
public class BundleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BundleService.class);

    @Autowired
    private Bundle bundle;

    @Autowired
    private APIxController apixController;

    @GET
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

        if (!bundle.exists(endpoint)) {
            bundle.add(endpoint);
            apixController.bootAttempt(this.getClass().getSimpleName() +
                " PUT {Endpoint: " + endpoint.getMethod() + " " + endpoint.getPath() + "}");
        } else {
            LOGGER.info("Endpoint already exists");
            throw new DuplicatedEndpointException("Endpoint [" + endpoint.getMethod() + " - " + endpoint.getPath() + "] already exists");
        }


        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    public Response update(Endpoint endpoint) {

        bundle.change(endpoint);

        apixController.bootAttempt(this.getClass().getSimpleName() +
            " POST {Endpoint: " + endpoint.getMethod() + " " + endpoint.getPath() + "}");

        return Response.ok().entity(bundle).build();
    }

}
