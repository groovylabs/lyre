package com.github.groovylabs.lyre.test;

import com.github.groovylabs.lyre.test.applications.EnableLyreApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("application-test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = EnableLyreApplication.class)
public class EnableLyreAnnotationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void enableLyreAnnotationTest() {

        ResponseEntity<String> response =
            restTemplate.getForEntity("http://127.0.0.1:9234/test/ping", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

}
