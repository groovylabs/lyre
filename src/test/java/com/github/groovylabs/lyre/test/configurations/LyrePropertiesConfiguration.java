package com.github.groovylabs.lyre.test.configurations;

import com.github.groovylabs.lyre.config.LyreProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.TestPropertySource;

@TestConfiguration
public class LyrePropertiesConfiguration {

    @Bean
    @Primary
    public LyreProperties lyreProperties() {
        LyreProperties lyreProperties = new LyreProperties();
        lyreProperties.setScanPath(System.getProperty("user.dir") + "/src/test/resources/endpoints");
        lyreProperties.setApiPath("test");
        lyreProperties.setDebug(true);
        return lyreProperties;
    }

}
