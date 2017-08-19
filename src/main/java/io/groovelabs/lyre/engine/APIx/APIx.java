package io.groovelabs.lyre.engine.APIx;

import io.groovelabs.lyre.domain.Bundle;
import io.groovelabs.lyre.domain.Endpoint;
import io.groovelabs.lyre.domain.setups.Countdown;
import io.groovelabs.lyre.engine.APIx.services.EndpointService;
import io.groovelabs.lyre.engine.interpreter.Interpreter;
import org.glassfish.hk2.api.Immediate;
import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;

@Immediate
@Component
public class APIx extends ResourceConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(APIx.class);

    // TODO need to be stored (memory, file, redis if configurable)
    public static Bundle bundle = null;

    private static Container container;

    public APIx() {
        config(this);
        new Interpreter(this);
    }


    public void boot(Bundle bundle) {

        APIx.bundle = bundle;

        if (APIx.container != null) {
            final ResourceConfig resourceConfig = this.createResources(bundle, null);
            APIx.container.reload(resourceConfig);

        } else {
            this.createResources(bundle, this);
        }
    }

    private void config(final ResourceConfig resourceConfig) {
        resourceConfig.registerInstances(new ContainerLifecycleListener() {
            @Override
            public void onStartup(final Container container) {
                System.out.println("Application has been started!");
                APIx.container = container;
            }

            @Override
            public void onReload(final Container container) {
                System.out.println("Application has been reloaded!");
            }

            @Override
            public void onShutdown(final Container container) {
                // ignore
            }
        });
    }

    private ResourceConfig createResources(Bundle bundle, ResourceConfig resourceConfig) {

        if (resourceConfig == null)
            resourceConfig = new ResourceConfig();

        for (Endpoint endpoint : bundle.getList()) {

            Resource.Builder resourceBuilder =
                Resource.builder().path(endpoint.getPath());

            resourceBuilder.addMethod(endpoint.getMethod().name())
                .consumes(endpoint.getConsumes())
                .produces(endpoint.getResponse().getProduces())
                .handledBy(new Inflector<ContainerRequestContext, Object>() {
                    @Override
                    public Response apply(ContainerRequestContext containerRequestContext) {

                        Countdown countdown = endpoint.getSetup().getCountdown();

                        if (countdown != null && countdown.getCalls() > 0) {
                            countdown.decrease();
                            return Response.status(endpoint.getSetup().getCountdown().getStatus().value()).build();
                        } else {


                            if (endpoint.getSetup().getIdle() > 0) {

                                try {
                                    Thread.sleep(endpoint.getSetup().getIdle());
                                } catch (InterruptedException e) {

                                }

                            }

                            return Response
                                .status(endpoint.getResponse().getStatus().value())
                                .entity(endpoint.getResponse().getData()).build();
                        }
                    }
                });

            resourceConfig.registerResources(resourceBuilder.build());
        }

        resourceConfig.register(EndpointService.class);

        return resourceConfig;
    }
}
