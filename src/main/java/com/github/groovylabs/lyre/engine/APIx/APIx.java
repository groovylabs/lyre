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

package com.github.groovylabs.lyre.engine.APIx;

import com.github.groovylabs.lyre.config.LyreProperties;
import com.github.groovylabs.lyre.config.NotFoundExceptionMapper;
import com.github.groovylabs.lyre.domain.Bundle;
import com.github.groovylabs.lyre.domain.Endpoint;
import com.github.groovylabs.lyre.domain.Event;
import com.github.groovylabs.lyre.domain.enums.EventAction;
import com.github.groovylabs.lyre.domain.enums.Queue;
import com.github.groovylabs.lyre.engine.APIx.controller.APIxListener;
import com.github.groovylabs.lyre.engine.APIx.filters.CORSFilter;
import com.github.groovylabs.lyre.engine.APIx.inflectors.APIxInflector;
import com.github.groovylabs.lyre.engine.APIx.services.BundleService;
import com.github.groovylabs.lyre.engine.APIx.services.LandingPageService;
import com.github.groovylabs.lyre.engine.APIx.swagger.SwaggerResource;
import com.github.groovylabs.lyre.engine.APIx.websocket.Dispatcher;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
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
import java.net.InetAddress;

@Component
public class APIx extends ResourceConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(APIx.class);

    @Autowired
    private LyreProperties lyreProperties;

    @Autowired
    private Dispatcher dispatcher;

    @Autowired
    private SwaggerResource swaggerResource;

    @Autowired
    private Bundle bundle;

    private static Container container;

    @PostConstruct
    public void APIx() {
        config(this);
    }

    public void boot() {

        LOGGER.info("Boot [STATUS]: Started");

        if (container != null) {
            final ResourceConfig resourceConfig = this.createResources(bundle, null);
            container.reload(resourceConfig);
        } else {
            this.createResources(bundle, this);
        }

    }

    private void config(final ResourceConfig resourceConfig) {
        resourceConfig.registerInstances(new ContainerLifecycleListener() {

            @Override
            public void onStartup(final Container container) {
                LOGGER.info("Lyre REST API Mock tool started");

                LOGGER.info("\u21B3 " + "Endpoints are available at: http://{}:{}{}/{}",
                    InetAddress.getLoopbackAddress().getHostAddress(),
                    lyreProperties.getPort(),
                    lyreProperties.getContextPath(),
                    lyreProperties.getApplicationPath());

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

            Resource.Builder resource =
                Resource.builder().path(endpoint.getPath());

            resource.addMethod(endpoint.getMethod().name())
                .consumes(endpoint.getConsumes())
                .handledBy(new APIxInflector(endpoint));

            resourceConfig.registerResources(resource.build());
        }

        swaggerResource.register(bundle, resourceConfig);
        resourceConfig.register(new MultiPartFeature());
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
