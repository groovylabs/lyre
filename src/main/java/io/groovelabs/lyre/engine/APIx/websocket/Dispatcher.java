package io.groovelabs.lyre.engine.APIx.websocket;

import io.groovelabs.lyre.engine.APIx.APIx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@Configurable
public class BundleController {

    @Autowired
    private MessageSendingOperations<String> messagingTemplate;

    @MessageMapping("/bundle")
    public void publish() throws Exception {

        System.out.println();

        this.messagingTemplate.convertAndSend("/topic/bundle", APIx.bundle);
    }

}
