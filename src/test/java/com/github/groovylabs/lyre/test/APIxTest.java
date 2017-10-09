package com.github.groovylabs.lyre.test;


import com.github.groovylabs.lyre.Lyre;
import com.github.groovylabs.lyre.domain.Bundle;
import com.github.groovylabs.lyre.engine.APIx.APIx;
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
public class APIxTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private Bundle bundle;

    @Autowired
    private APIx apix;

    @Test
    public void apixTest() {

        // apix & bundle instance can not be null
        assertThat(apix).isNotNull();
        assertThat(bundle).isNotNull();

        // check if GET apix is active
        ResponseEntity<String> response
            = restTemplate.getForEntity("/test/apix", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

}
