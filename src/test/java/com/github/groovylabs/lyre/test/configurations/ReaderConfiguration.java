package com.github.groovylabs.lyre.test.configurations;

import com.github.groovylabs.lyre.config.LyreProperties;
import com.github.groovylabs.lyre.engine.interpreter.Interpreter;
import com.github.groovylabs.lyre.engine.reader.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Import({
    LyrePropertiesConfiguration.class,
    ResourcesConfiguration.class
})
@TestConfiguration
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

