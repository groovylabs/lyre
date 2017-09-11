package groovylabs.lyre.config;

import groovylabs.lyre.Lyre;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

public class LyreConfiguration implements CommandLineRunner {

    public final static String LYRE_PROPERTIES_PREFIX = "lyre";

    @Override
    public void run(String... args) throws Exception {
        SpringApplication.run(Lyre.class, args);
    }

}
