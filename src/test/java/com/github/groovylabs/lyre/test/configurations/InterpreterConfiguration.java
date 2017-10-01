package com.github.groovylabs.lyre.test.configurations;

import com.github.groovylabs.lyre.config.LyreProperties;
import com.github.groovylabs.lyre.domain.factories.FactoryConfiguration;
import com.github.groovylabs.lyre.engine.APIx.APIx;
import com.github.groovylabs.lyre.engine.APIx.controller.APIxController;
import com.github.groovylabs.lyre.engine.interpreter.Interpreter;
import com.github.groovylabs.lyre.engine.reader.Reader;
import com.github.groovylabs.lyre.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.*;

@Profile("test")
@Configuration
@Import({
    LyrePropertiesConfiguration.class,
    ResourcesConfiguration.class,
    FactoryConfiguration.class
})
public class InterpreterConfiguration {

    @Autowired
    private LyreProperties lyreProperties;

    @MockBean
    private APIxController apixController;

    @Bean
    @Primary
    public Reader reader() {
        return new Reader();
    }

    @Bean
    @Primary
    public Validator validator() {
        return new Validator();
    }

    @Bean
    @Primary
    public Interpreter interpreter() {
        return new Interpreter();
    }

}
