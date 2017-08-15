package lyre.APIx;

import lyre.APIx.samples.PingService;
import lyre.domain.Bundle;
import lyre.domain.Endpoint;
import lyre.interpreter.Interpreter;
import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.model.Resource;
import org.springframework.stereotype.Component;

import javax.ws.rs.container.ContainerRequestContext;

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
                        public Object apply(ContainerRequestContext containerRequestContext) {
                            return endpoint.getData();
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
