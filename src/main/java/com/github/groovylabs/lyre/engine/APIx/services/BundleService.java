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
    public Response get() {
        if (bundle.isEmpty())
            return Response.noContent().build();
        else
            return Response.ok().entity(bundle).build();
    }

    @DELETE
    public Response delete() {
        bundle.clear();
        apixController.bootAttempt(this.getClass().getSimpleName() + " DELETE");
        return Response.ok().build();
    }

    @POST
    public Response post(Bundle bundle) {

        if (!bundle.isEmpty()) {
            for (Endpoint endpoint : bundle.getEndpoints()) {
                if (bundle.exists(endpoint))
                    bundle.update(endpoint);
                else
                    bundle.add(endpoint);
            }

            apixController.bootAttempt(this.getClass().getSimpleName() +
                " POST {Bundle}");
        } else
            throw new BadRequestException("Malformed bundle entity");

        return Response.ok().entity(bundle).build();
    }

    @PUT
    public Response put(Bundle bundle) {

        if (bundle != null && bundle.getEndpoints() != null && !bundle.getEndpoints().isEmpty()) {
            bundle.clear();
            bundle.addAll(bundle.getEndpoints());
            apixController.bootAttempt(this.getClass().getSimpleName() +
                " PUT {Bundle}");
        } else
            throw new BadRequestException("Malformed bundle entity");

        return Response.status(Response.Status.CREATED).build();
    }
}
