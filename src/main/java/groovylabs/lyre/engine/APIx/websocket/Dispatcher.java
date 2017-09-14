package groovylabs.lyre.engine.APIx.websocket;

import groovylabs.lyre.domain.Event;
import groovylabs.lyre.domain.enums.Queue;
import groovylabs.lyre.engine.APIx.APIx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Component;

@Component
public class Dispatcher {

    private String queuePrefix = "/registry/";

    @Autowired
    private MessageSendingOperations<String> messagingTemplate;

    public void publish(Event event) {
        if (event != null && event.getQueue() != null && event.getAction() != null) {
            this.messagingTemplate.convertAndSend(queuePrefix + event.getQueue(), event);
        }
    }

}
