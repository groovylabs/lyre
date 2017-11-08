package com.github.groovylabs.lyre.test.services;

import com.github.groovylabs.lyre.Lyre;
import com.github.groovylabs.lyre.test.configurations.LyrePropertiesConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Import({LyrePropertiesConfiguration.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Lyre.class)
public class HealthServiceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void healthServiceTest() {

        ResponseEntity<String> response = restTemplate.getForEntity("/test/health", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
