package io.groovelabs.lyre.engine.APIx.websocket;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class APIxSocket extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/apixsocket").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/registry");
        config.setApplicationDestinationPrefixes("/app");
    }

}

// https://github.com/bijukunjummen/spring-websocket-chat-sample/blob/master/src/main/java/bk/chat/web/ChatController.java

// https://spring.io/guides/gs/messaging-stomp-websocket/
