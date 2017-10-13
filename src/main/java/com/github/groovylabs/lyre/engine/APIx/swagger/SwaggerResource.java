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
import com.github.groovylabs.lyre.engine.APIx.swagger.implementations.ParameterInterfaceImpl;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import io.swagger.models.*;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.model.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class SwaggerResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerResource.class);

    @Autowired
    private LyreProperties lyreProperties;

    private Swagger buildSwagger(Bundle bundle) {

        // ref https://github.com/OAI/OpenAPI-Specification/tree/master/examples/v2.0/json

        Swagger swagger = new Swagger();
        swagger.setInfo(swaggerInfo());
        swagger.setBasePath("/" + lyreProperties.getApplicationPath());
        swagger.setSchemes(Stream.of(Scheme.HTTP).collect(Collectors.toList()));
        swagger.setConsumes(Stream.of("application/json").collect(Collectors.toList()));
        swagger.setProduces(Stream.of("application/json").collect(Collectors.toList()));

        LOGGER.info("Boot [STATUS]: Creating Swagger endpoints");

        for (Endpoint endpoint : bundle.getEndpoints())
            buildSwaggerPath(swagger, endpoint);

        LOGGER.info("Boot [STATUS]: Swagger endpoint created successfully");

        return swagger;
    }

    public void register(Bundle bundle, ResourceConfig resourceConfig) {

        if (lyreProperties.isEnableSwaggerDoc()) {

            Swagger swagger = buildSwagger(bundle);

            Resource.Builder resource = Resource.builder();
            resource.path("/docs")
                .addMethod(HttpMethod.GET)
                .produces(MediaType.APPLICATION_JSON)
                .handledBy(new SwaggerInflector(swagger));

            resourceConfig.registerResources(resource.build());
            resourceConfig.register(SwaggerSerializers.class);
        }
    }

    private Info swaggerInfo() {

        Info info = new Info();

        info.version("1.0.0");
        info.title("Lyre Swagger UI");
        info.description("A Mock API exposed to call Lyre REST endpoints.");

        //TODO contact info

        License license = new License();
        license.name("MIT");
        license.setUrl("https://github.com/groovylabs/lyre/blob/master/LICENSE");

        info.license(new License());

        return info;
    }

    private void buildSwaggerPath(Swagger swagger, Endpoint endpoint) {

        try {
            Operation operation = new Operation();
            operation.consumes(endpoint.getConsumes());

            if (!StringUtils.isEmpty(endpoint.getData()))
                buildSwaggerBodyParam(operation);

            operation.addResponse(endpoint.getResponse().getStatus().toString(), new io.swagger.models.Response());

            swagger.path(endpoint.getPath(), new Path().set(endpoint.getMethod().toString().toLowerCase(), operation));

        } catch (Exception e) {
            LOGGER.error("[SwaggerIntegration] Error during build swaggerEndpoint object! PATH=[{}] / METHOD=[{}]",
                endpoint.getPath(), endpoint.getMethod());
        }
    }

    private void buildSwaggerBodyParam(Operation operation) {
        io.swagger.models.Response response = new io.swagger.models.Response();
        ParameterInterfaceImpl param = new ParameterInterfaceImpl();

        param.setName("Body");
        param.setIn("body");
        param.setRequired(true);
        param.setDescription("Expected declared object in lyre file by the endpoint");

        response.setDescription(Response.Status.NOT_ACCEPTABLE.toString());

        operation.addParameter(param);
        operation.addResponse(String.valueOf(Response.Status.NOT_ACCEPTABLE.getStatusCode()), response);
    }

}
