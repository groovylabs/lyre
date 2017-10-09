package com.github.groovylabs.lyre;

import com.github.groovylabs.lyre.config.LyreProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@EnableLyre
@SpringBootApplication
public class LyreTestApplication {

    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(LyreTestApplication.class);
        application.run();

    }

    @Bean
    LyreProperties lyreProperties() {
        LyreProperties lyreProperties = new LyreProperties();
        lyreProperties.setScanPath(System.getProperty("user.dir") + "/src/test/resources/endpoints");
        lyreProperties.setApiPath("test");
        lyreProperties.setPort(9234);
        return lyreProperties;
    }
}
