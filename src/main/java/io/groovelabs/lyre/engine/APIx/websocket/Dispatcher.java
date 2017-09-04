package io.groovelabs.lyre.engine.APIx.websocket;

import io.groovelabs.lyre.domain.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class Dispatcher {

    private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/bundle")
    public void dispatchBundle(Bundle bundle) {
        this.template.convertAndSend("/registry/bundle", bundle);
    }

}
