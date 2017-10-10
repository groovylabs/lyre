package com.github.groovylabs.lyre.config;

import com.github.groovylabs.lyre.EnableLyre;
import com.github.groovylabs.lyre.Lyre;
import com.github.groovylabs.lyre.utils.RunnerUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;

public class LyreRunner implements ApplicationRunner {

    public final static String LYRE_PROPERTIES_PREFIX = "lyre";

    @Override
    public void run(ApplicationArguments args) throws Exception {

        SpringApplication app = new SpringApplication(Lyre.class);
        String[] arguments = RunnerUtils.buildArguments(app.getMainApplicationClass().getAnnotation(EnableLyre.class));
        app.setMainApplicationClass(Lyre.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(arguments);
    }

}
