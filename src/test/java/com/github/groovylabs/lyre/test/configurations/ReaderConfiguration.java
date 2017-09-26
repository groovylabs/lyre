package com.github.groovylabs.lyre.test.configurations;

import com.github.groovylabs.lyre.config.LyreProperties;
import com.github.groovylabs.lyre.engine.interpreter.Interpreter;
import com.github.groovylabs.lyre.engine.reader.Reader;
import com.github.groovylabs.lyre.engine.scanner.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.*;

@Profile("test")
@Configuration
@Import({
    LyrePropertiesConfiguration.class,
    ResourcesConfiguration.class
})
public class ReaderConfiguration {

    @Autowired
    private LyreProperties lyreProperties;

    @MockBean
    private Interpreter interpreter;

    @Bean
    @Primary
    public Reader reader() {
        Reader reader = new Reader();
        return reader;
    }

}
