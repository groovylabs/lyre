package com.github.groovylabs.lyre.engine.APIx;

import com.github.groovylabs.lyre.config.LyreProperties;
import com.github.groovylabs.lyre.config.NotFoundExceptionMapper;
import com.github.groovylabs.lyre.domain.Bundle;
import com.github.groovylabs.lyre.domain.Endpoint;
import com.github.groovylabs.lyre.domain.Event;
import com.github.groovylabs.lyre.domain.appliers.Countdown;
import com.github.groovylabs.lyre.domain.enums.EventAction;
import com.github.groovylabs.lyre.domain.enums.Queue;
import com.github.groovylabs.lyre.domain.factories.LogFactory;
import com.github.groovylabs.lyre.engine.APIx.controller.APIxListener;
import com.github.groovylabs.lyre.engine.APIx.filters.CORSFilter;
import com.github.groovylabs.lyre.engine.APIx.services.BundleService;
import com.github.groovylabs.lyre.engine.APIx.services.LandingPageService;
import com.github.groovylabs.lyre.engine.APIx.swagger.SwaggerIntegration;
import com.github.groovylabs.lyre.engine.APIx.websocket.Dispatcher;
import com.github.groovylabs.lyre.validator.Validator;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.assertj.core.util.Preconditions;
import org.assertj.core.util.Strings;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;

@Component
public class APIx extends ResourceConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(APIx.class);

    @Autowired
    private LyreProperties lyreProperties;

    @javax.annotation.Resource(name = "&log")
    private LogFactory logFactory;

    @Autowired
    private Dispatcher dispatcher;

    @Autowired
    private SwaggerIntegration swaggerIntegration;

    @Autowired
    private Bundle bundle;

    @Autowired
    private Validator validator;

    private static Container container;

    @PostConstruct
    public void APIx() {
        config(this);
    }

    public void boot() {

        LOGGER.info("Boot [STATUS]: Started");

        if (com.github.groovylabs.lyre.engine.APIx.APIx.container != null) {
            final ResourceConfig resourceConfig = this.createResources(bundle, null);
            com.github.groovylabs.lyre.engine.APIx.APIx.container.reload(resourceConfig);
        } else {
            this.createResources(bundle, this);
        }

    }

    private void config(final ResourceConfig resourceConfig) {
        resourceConfig.registerInstances(new ContainerLifecycleListener() {

            @Override
            public void onStartup(final Container container) {
                LOGGER.info("Lyre REST API Mock tool started");

                LOGGER.info("\u21B3 " + "Endpoints are available at: http://{}:{}/{}{}",
                    InetAddress.getLoopbackAddress().getHostAddress(),
                    lyreProperties.getPort(),
                    (!StringUtils.isEmpty(lyreProperties.getContextPath()) ? lyreProperties.getContextPath() + "/" : ""),
                    lyreProperties.getApiPath());

                com.github.groovylabs.lyre.engine.APIx.APIx.container = container;
            }

            @Override
            public void onReload(final Container container) {
                LOGGER.info("Lyre Mock Application [RELOADED]");
            }

            @Override
            public void onShutdown(final Container container) {
            }
        });
    }

    private ResourceConfig createResources(Bundle bundle, ResourceConfig resourceConfig) {

        Event bundleEvent = new Event(Queue.BUNDLE, EventAction.UPDATE);

        if (resourceConfig == null) {
            resourceConfig = new ResourceConfig();
            bundleEvent.setAction(EventAction.NEW);
        }

        LOGGER.info("Boot [STATUS]: Creating endpoints into APIx engine");

        for (Endpoint endpoint : bundle.getEndpoints()) {

            Resource.Builder resourceBuilder =
                Resource.builder().path(endpoint.getPath());

            resourceBuilder.addMethod(endpoint.getMethod().name())
                .consumes(endpoint.getConsumes())
                .handledBy(new Inflector<ContainerRequestContext, Object>() {

                    @Context
                    private HttpServletRequest request;

                    @Override
                    public Response apply(ContainerRequestContext containerRequestContext) {

                        dispatcher.publish(logFactory.logger(endpoint, request).info("Endpoint called.").event());

                        Countdown countdown = endpoint.getSetup().getCountdown();

                        if (!Strings.isNullOrEmpty(endpoint.getData())) {
                            //TODO: Make a method that will be looking for the attributes of the object, not the of object as string.
                            String requestObject = validator.getEntityBody(containerRequestContext);

                            if (!endpoint.getData().equals(requestObject))
                                return Response.status(Response.Status.NOT_ACCEPTABLE).build();

                        }

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
                                .entity(endpoint.getResponse().getData()).type(endpoint.getResponse().getProduces()).build();
                        }
                    }
                });

            resourceConfig.registerResources(resourceBuilder.build());
        }

        if (lyreProperties.isEnableSwaggerDoc()) {
            swaggerIntegration.enableSwagger(bundle, this);
            resourceConfig.register(SwaggerSerializers.class);
        }

        register(new MultiPartFeature());
        resourceConfig.register(APIxListener.class);
        resourceConfig.register(CORSFilter.class);
        resourceConfig.register(BundleService.class);
        resourceConfig.register(LandingPageService.class);
        resourceConfig.register(NotFoundExceptionMapper.class);

        dispatcher.publish(bundleEvent);

        LOGGER.info("Boot [STATUS]: Completed");

        return resourceConfig;
    }

}
