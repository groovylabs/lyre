package com.github.groovylabs.lyre.test;

import com.github.groovylabs.lyre.Lyre;
import com.github.groovylabs.lyre.test.configurations.LyrePropertiesConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("rest-test")
@RunWith(SpringRunner.class)
@Import({
    LyrePropertiesConfiguration.class
})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Lyre.class)
public class LyreTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void lyreTestRunWithAnnotation() {

    }

    @Test
    public void lyreTestValidEndpoints() {

    }

    @Test
    public void lyreTestInvalidEndpoints() {

    }

}
