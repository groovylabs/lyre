package com.github.groovylabs.lyre.test.configurations;

import com.github.groovylabs.lyre.test.tools.Resources;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class ResourcesConfiguration {

    @Bean
    @Primary
    public Resources resources() {
        return new Resources();
    }

}
