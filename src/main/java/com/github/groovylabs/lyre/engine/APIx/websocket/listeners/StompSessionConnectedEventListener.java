package com.github.groovylabs.lyre.engine.APIx.websocket.listeners;

import com.github.groovylabs.lyre.config.LyreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Component
public class StompSessionConnectedEventListener implements ApplicationListener<SessionConnectedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StompSessionConnectedEventListener.class);

    @Autowired
    private LyreProperties lyreProperties;

    @Override
    public void onApplicationEvent(SessionConnectedEvent sessionConnectedEvent) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionConnectedEvent.getMessage());

        if (lyreProperties.isDebug())
            LOGGER.info(headerAccessor.toString());
    }
}
