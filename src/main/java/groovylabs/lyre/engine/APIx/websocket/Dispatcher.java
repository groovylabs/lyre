package groovylabs.lyre.engine.APIx.websocket;

import groovylabs.lyre.engine.APIx.APIx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Component;

@Component
public class Dispatcher {

    @Autowired
    private MessageSendingOperations<String> messagingTemplate;

    @MessageMapping("/bundle")
    public void publish() {
        this.messagingTemplate.convertAndSend("/registry/bundle", APIx.bundle);
    }

}
