package boot;

import groovylabs.lyre.LyreApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@LyreApplication
@SpringBootApplication
public class TestBoot {

    public static void main(String[] args) {
        SpringApplication.run(TestBoot.class);
    }

}
