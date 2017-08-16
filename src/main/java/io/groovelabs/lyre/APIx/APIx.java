package io.groovelabs.lyre.APIx;

import io.groovelabs.lyre.domain.Bundle;
import io.groovelabs.lyre.APIx.samples.PingService;
import io.groovelabs.lyre.domain.Endpoint;
import io.groovelabs.lyre.interpreter.Interpreter;
import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.model.Resource;
import org.springframework.stereotype.Component;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;

@Component
public class APIx extends ResourceConfig {

    public APIx() {

        Interpreter interpreter = new Interpreter();

        try {

            Bundle bundle = interpreter.interpret();

            for (Endpoint endpoint : bundle.getList()) {

                Resource.Builder resourceBuilder =
                    Resource.builder().path(endpoint.getPath());

                resourceBuilder.addMethod(endpoint.getMethod().name())
                    .produces("text/plain")
                    .handledBy(new Inflector<ContainerRequestContext, Object>() {
                        @Override
                        public Response apply(ContainerRequestContext containerRequestContext) {
                            return Response.status(endpoint.getStatus().value()).entity(endpoint.getData()).build();
                        }
                    });

                registerResources(resourceBuilder.build());
            }

            register(PingService.class);

        } catch (Exception e) {
            System.out.println("APIx error");
        }
    }
}
