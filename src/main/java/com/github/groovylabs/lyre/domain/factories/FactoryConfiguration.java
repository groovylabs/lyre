package com.github.groovylabs.lyre.domain.factories;

import com.github.groovylabs.lyre.domain.Bundle;
import com.github.groovylabs.lyre.domain.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FactoryConfiguration {

    @Bean
    public BundleFactory bundleFactory() {
        return new BundleFactory();
    }

    @Bean
    public Bundle bundle() {
        return bundleFactory().getObject();
    }

    @Bean(name = "log")
    public LogFactory logFactory() {
        return new LogFactory();
    }

    @Bean
    public Log log() {
        return logFactory().getObject();
    }

}
