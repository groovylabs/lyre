package io.groovelabs.lyre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@Configuration
@EnableConfigurationProperties(LyreProperties.class)
@SpringBootApplication
public class Lyre {

    public final static String LYRE_PROPERTIES_PREFIX = "io/groovelabs/lyre";

    @Autowired
    private LyreProperties lyreProperties;

    public static void main(String[] args) {
        SpringApplication.run(Lyre.class);
    }
}
