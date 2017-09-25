package com.github.groovylabs.lyre.test.mocks;

import com.github.groovylabs.lyre.engine.APIx.websocket.Dispatcher;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.core.MessageSendingOperations;

@Profile("test")
@Configuration
public class DispatcherMockConfiguration {

    @Bean
    @Primary
    public Dispatcher dispatcher() {
        return Mockito.mock(Dispatcher.class);
    }

    @Bean
    @Primary
    public MessageSendingOperations messageSendingOperations() {
        return null;
    }

}
