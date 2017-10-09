/*
package com.github.groovylabs.lyre.test;

import com.github.groovylabs.lyre.test.applications.LyreTestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = LyreTestApplication.class)
public class LyreTest {

    private RestTemplate restTemplate;

    @Test
    public void enableLyreAnnotationTest() {

        restTemplate = new RestTemplate();

        ResponseEntity<String> response =
            restTemplate.getForEntity("http://127.0.0.1:9234/test/ping", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

}
*/
