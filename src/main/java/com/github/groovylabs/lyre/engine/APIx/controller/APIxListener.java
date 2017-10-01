package com.github.groovylabs.lyre.engine.APIx.controller;

import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class APIxListener implements ApplicationEventListener {

    @Autowired
    private APIxController controller;

    @Override
    public void onEvent(ApplicationEvent event) {

    }

    @Override
    public RequestEventListener onRequest(RequestEvent requestEvent) {
        return new APIxRequestEventListener();
    }

    public class APIxRequestEventListener implements RequestEventListener {

        @Override
        public void onEvent(RequestEvent event) {
            switch (event.getType()) {
                case RESOURCE_METHOD_START:
                    controller.increase();
                    break;
                case FINISHED:
                    controller.decrease();
                    break;
            }

        }
    }
}
