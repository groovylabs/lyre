package io.groovelabs.lyre;

import io.groovelabs.lyre.config.LyreProperties;
import io.groovelabs.lyre.engine.APIx.APIx;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(LyreProperties.class)
public class Lyre {

    @Autowired
    private LyreProperties lyreProperties;

    public static void main(String[] args) {
        SpringApplication.run(Lyre.class, args);
    }

    @Bean
    public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() {
        return (container -> {
            container.setContextPath(lyreProperties.getContextPath());
            container.setPort(lyreProperties.getPort());
        });
    }

}
