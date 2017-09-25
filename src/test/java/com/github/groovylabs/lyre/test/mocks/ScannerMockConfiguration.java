package com.github.groovylabs.lyre.test.mocks;

import com.github.groovylabs.lyre.engine.scanner.Scanner;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class ScannerMockConfiguration {

    @Bean
    @Primary
    public Scanner scanner() {
        return Mockito.mock(Scanner.class);
    }

}
