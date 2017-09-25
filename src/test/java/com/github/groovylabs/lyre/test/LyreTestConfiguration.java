package com.github.groovylabs.lyre.test;

import com.github.groovylabs.lyre.config.LyreProperties;
import com.github.groovylabs.lyre.engine.scanner.Scanner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class LyreTestConfiguration {

    @Bean
    @Primary
    public LyreProperties lyreProperties() {
        LyreProperties lyreProperties = new LyreProperties();
        lyreProperties.setScanPath(System.getProperty("user.dir") + "/src/test/resources");
        lyreProperties.setApiPath("api-test");
        lyreProperties.setDebug(true);
        return lyreProperties;
    }

//    @Bean
//    @Primary
//    public Scanner scanner() {
//        return new Scanner();
//    }

}
