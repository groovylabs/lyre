package boot;

import com.github.groovylabs.lyre.EnableLyre;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableLyre
@SpringBootApplication
public class TestBoot {

    public static void main(String[] args) {
        SpringApplication.run(TestBoot.class);
    }

}
