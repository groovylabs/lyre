package com.github.groovylabs.lyre.test.configurations;

import com.github.groovylabs.lyre.test.tools.Resources;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@TestConfiguration
public class ResourcesConfiguration {

    @Bean
    @Primary
    public Resources resources() {
        return new Resources();
    }

}
