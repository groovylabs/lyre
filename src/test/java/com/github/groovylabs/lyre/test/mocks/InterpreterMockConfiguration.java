package com.github.groovylabs.lyre.test.mocks;

import com.github.groovylabs.lyre.engine.interpreter.Interpreter;
import com.github.groovylabs.lyre.validator.Validator;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class InterpreterMockConfiguration {

    @Bean
    @Primary
    public Interpreter interpreter() {
        return Mockito.mock(Interpreter.class);
    }

    @Bean
    @Primary
    public Validator validator() {
        return Mockito.mock(Validator.class);
    }

}
