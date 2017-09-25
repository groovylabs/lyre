package com.github.groovylabs.lyre.test.mocks;

import com.github.groovylabs.lyre.engine.reader.Reader;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class ReaderMockConfiguration {

    @Bean
    @Primary
    public Reader reader() {
        return Mockito.mock(Reader.class);
    }

}
