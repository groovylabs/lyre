package groovylabs.lyre.engine.APIx;

import groovylabs.lyre.config.NotFoundExceptionMapper;
import groovylabs.lyre.domain.Bundle;
import groovylabs.lyre.domain.Endpoint;
import groovylabs.lyre.domain.appliers.Countdown;
import groovylabs.lyre.engine.APIx.filters.CORSFilter;
import groovylabs.lyre.engine.APIx.services.BundleService;
import groovylabs.lyre.engine.APIx.websocket.Dispatcher;
import groovylabs.lyre.engine.interpreter.Interpreter;
import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;

@Component
public class APIx extends ResourceConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(APIx.class);

    @Autowired
    private Dispatcher dispatcher;

    // TODO need to be stored (memory, file, redis if configurable)
    public static Bundle bundle = null;

    private static Container container;

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
                LOGGER.info("Jersey Application started");
                APIx.container = container;
            }

            @Override
            public void onReload(final Container container) {
                LOGGER.info("Jersey Application reloaded");
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

        for (Endpoint endpoint : bundle.getEndpoints()) {

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
                            return Response
                                .status(countdown.getStatus().value())
                                .entity(countdown.getStatus().getReasonPhrase()).build();
                        } else {

                            if (endpoint.getTimer().idle() > 0) {

                                try {
                                    Thread.sleep(endpoint.getTimer().idle());
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

        resourceConfig.register(CORSFilter.class);
        resourceConfig.register(BundleService.class);
        resourceConfig.register(NotFoundExceptionMapper.class);

        dispatcher.publish();

        return resourceConfig;
    }

}
