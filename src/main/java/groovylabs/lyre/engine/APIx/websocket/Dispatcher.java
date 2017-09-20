package groovylabs.lyre.engine.APIx.websocket;

import groovylabs.lyre.domain.Endpoint;
import groovylabs.lyre.domain.Event;
import groovylabs.lyre.domain.Log;
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

    public void publish(Event<?> event) {
        if (event != null && event.getQueue() != null && event.getAction() != null) {

            String queue = "";

            switch (event.getQueue()) {
                case BUNDLE:
                    queue = queuePrefix + event.getQueue();
                    break;
                case LOG:
                    if (((Log) event.getSource()).getTarget() instanceof Endpoint) {
                        queue = queuePrefix + event.getQueue() + "/" + ((Endpoint) ((Log) event.getSource()).getTarget()).getHash();
                    }

                    break;
            }

            this.messagingTemplate.convertAndSend(queue, event);
        }
    }

}
