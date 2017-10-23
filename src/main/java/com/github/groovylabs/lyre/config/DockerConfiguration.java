package com.github.groovylabs.lyre.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
@Profile("docker")
@EnableConfigurationProperties(LyreProperties.class)
@EnableAutoConfiguration
public class DockerConfiguration {

    @Autowired
    private LyreProperties lyreProperties;

    @Bean
    public EmbeddedServletContainerCustomizer embeddedServletContainerTest() {
        return (container -> {

            lyreProperties.setPort(8080);

            container.setContextPath(lyreProperties.getContextPath());

            if (!lyreProperties.isEnableRemoteConnections()) {
                try {
                    InetAddress inetAddress = InetAddress.getByAddress(new byte[]{127, 0, 0, 1});
                    container.setAddress(inetAddress);
                } catch (UnknownHostException e) {
                    //supressed exception
                }
            }

            container.setPort(lyreProperties.getPort());
        });
    }

}
