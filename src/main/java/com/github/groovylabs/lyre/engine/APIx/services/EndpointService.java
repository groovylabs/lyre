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
import com.github.groovylabs.lyre.engine.APIx.controller.APIxController;
import com.github.groovylabs.lyre.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path(value = "/endpoint")
@Produces(MediaType.APPLICATION_JSON)
public class EndpointService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EndpointService.class);

    @Autowired
    private Bundle bundle;

    @Autowired
    private APIxController apixController;

    @Autowired
    private Validator validator;

    @GET
    public Response get(@NotNull @QueryParam("method") String method, @NotNull @QueryParam("path") String path) {
        if (bundle.isEmpty())
            throw new NotFoundException("Bundle is empty");
        else {

            Endpoint endpoint = bundle.find(method, path);

            if (endpoint == null)
                throw new NotFoundException("Endpoint does not exist");

            return Response.ok().entity(endpoint).build();
        }
    }

    @DELETE
    public Response delete(@NotNull @QueryParam("method") String method, @NotNull @QueryParam("path") String path) {
        bundle.remove(method, path);
        apixController.bootAttempt(this.getClass().getSimpleName() + " DELETE");
        return Response.ok().build();
    }

    @POST
    public Response post(Endpoint endpoint) {

        if (validator.check(endpoint)) {

            if (bundle.exists(endpoint))
                bundle.update(endpoint);
            else
                throw new NotFoundException("Endpoint does not exist");

            apixController.bootAttempt(this.getClass().getSimpleName() +
                " POST {Endpoint method:[" + endpoint.getMethod().name() + "] path:[" + endpoint.getPath() + "]}");

        } else
            throw new BadRequestException("Malformed endpoint entity");

        return Response.ok().entity(endpoint).build();
    }

    @PUT
    public Response put(Endpoint endpoint) {

        if (validator.check(endpoint)) {

            if (bundle.exists(endpoint)) {
                bundle.update(endpoint);
            } else
                bundle.add(endpoint);

        } else
            throw new BadRequestException("Malformed endpoint entity");

        return Response.status(Response.Status.CREATED).build();
    }
}
