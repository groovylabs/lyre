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

package com.github.groovylabs.lyre.engine.APIx.swagger;

import com.github.groovylabs.lyre.config.LyreProperties;
import com.github.groovylabs.lyre.domain.Bundle;
import com.github.groovylabs.lyre.domain.Endpoint;
import com.github.groovylabs.lyre.engine.APIx.inflectors.SwaggerInflector;
import com.github.groovylabs.lyre.engine.APIx.swagger.resources.LyreSwagger;
import com.github.groovylabs.lyre.engine.APIx.swagger.resources.SwaggerHelper;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import io.swagger.models.Swagger;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.model.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.MediaType;

@Component
public class SwaggerResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerResource.class);

    @Autowired
    private LyreProperties lyreProperties;

    @Autowired
    private SwaggerHelper helper;

    public void register(Bundle bundle, ResourceConfig resourceConfig) {

        if (lyreProperties.isEnableSwagger()) {

            Swagger swaggerApi = buildSwaggerApi(bundle);

            if (swaggerApi != null) {
                Resource.Builder resource = Resource.builder();
                resource.path("/swagger")
                    .addMethod(HttpMethod.GET)
                    .produces(MediaType.APPLICATION_JSON)
                    .handledBy(new SwaggerInflector(swaggerApi));

                resourceConfig.registerResources(resource.build());
            }

            Swagger swaggerManagement = buildSwaggerManagement();

            if (swaggerManagement != null) {
                Resource.Builder resource = Resource.builder();
                resource.path("/management")
                    .addMethod(HttpMethod.GET)
                    .produces(MediaType.APPLICATION_JSON)
                    .handledBy(new SwaggerInflector(swaggerManagement));

                resourceConfig.registerResources(resource.build());
            }
        }

        resourceConfig.register(ApiListingResource.class);
        resourceConfig.register(SwaggerSerializers.class);
        SwaggerSerializers.setPrettyPrint(true);
    }

    private Swagger buildSwaggerApi(Bundle bundle) {

        Swagger swagger = new LyreSwagger(lyreProperties.getContextPath(), lyreProperties.getApplicationPath());

        LOGGER.info("Creating swagger api...");

        for (Endpoint endpoint : bundle.getEndpoints())
            helper.buildSwaggerApiPath(swagger, endpoint);

        LOGGER.info("Swagger api created");

        return swagger;
    }

    private Swagger buildSwaggerManagement() {
        Swagger swagger = new LyreSwagger( "Manage your endpoints and server configuration.", lyreProperties.getContextPath(), lyreProperties.getApplicationPath());

        LOGGER.info("Creating swagger management...");

        helper.buildSwaggerManagement(swagger);

        LOGGER.info("Swagger management created");

        return swagger;
    }

}
