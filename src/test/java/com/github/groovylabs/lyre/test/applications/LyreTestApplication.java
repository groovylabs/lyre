package com.github.groovylabs.lyre.test.applications;

import com.github.groovylabs.lyre.EnableLyre;
import com.github.groovylabs.lyre.test.configurations.LyrePropertiesConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Profile("integration-test")
@EnableLyre
@Import({LyrePropertiesConfiguration.class})
@SpringBootApplication
public class LyreTestApplication {

    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(LyreTestApplication.class);
        application.run();

    }
}
