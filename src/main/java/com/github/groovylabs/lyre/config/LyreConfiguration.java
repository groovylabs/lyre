package com.github.groovylabs.lyre.config;

import com.github.groovylabs.lyre.Lyre;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

public class LyreConfiguration implements CommandLineRunner {

    public final static String LYRE_PROPERTIES_PREFIX = "lyre";

    @Override
    public void run(String... args) throws Exception {

        SpringApplication app = new SpringApplication(Lyre.class);
        app.setMainApplicationClass(Lyre.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);

    }
}
