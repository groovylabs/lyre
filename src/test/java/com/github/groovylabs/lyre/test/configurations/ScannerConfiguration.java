package com.github.groovylabs.lyre.test.configurations;

import com.github.groovylabs.lyre.config.LyreProperties;
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
public class ScannerConfiguration {

    @Autowired
    private LyreProperties lyreProperties;

    @MockBean
    private Reader reader;

    @Bean
    @Primary
    public Scanner scanner() {
        Scanner scanner = new Scanner();
        scanner.setLyreProperties(lyreProperties);
        scanner.setReader(reader);
        return scanner;
    }

}
