package com.github.groovylabs.lyre.test;

import com.github.groovylabs.lyre.Lyre;
import com.github.groovylabs.lyre.domain.Bundle;
import com.github.groovylabs.lyre.domain.Endpoint;
import com.github.groovylabs.lyre.test.configurations.LyrePropertiesConfiguration;
import com.github.groovylabs.lyre.test.configurations.ResourcesConfiguration;
import com.github.groovylabs.lyre.test.tools.Resources;
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
@Import({
    LyrePropertiesConfiguration.class,
    ResourcesConfiguration.class,
})
@SpringBootTest(properties = {"lyre.port=8082"}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = Lyre.class)
public class ServicesTest {

    private static final String PATH_HEALTH_ENDPOINT = "/test/health";
    private static final String PATH_BUNDLE_ENDPOINT = "/test/bundle";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private Resources resources;

    @Autowired
    private Bundle bundle;

    @Test
    public void healthServiceTest() {

        // check if lyre is running up
        ResponseEntity<String> response = restTemplate.getForEntity(PATH_HEALTH_ENDPOINT, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void bundleServiceGetEmptyTest() {

        this.bundle.clear();

        // get empty bundle list
        ResponseEntity<String> response = restTemplate.getForEntity(PATH_BUNDLE_ENDPOINT, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void bundleServiceGetTest() {

        // putting endpoint inside a bundle list
        Endpoint endpoint = resources.createEndpoint("GET", "/ping", "200");
        bundle.add(endpoint);

        // get bundle list
        ResponseEntity<String> response = restTemplate.getForEntity(PATH_BUNDLE_ENDPOINT, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }


    public void bundleServiceDeleteTest() {

        if (bundle.getEndpoints().size() == 0)
            bundle.add(resources.createEndpoint("GET", "/ping", "200"));

        assertThat(bundle.getEndpoints()).isNotEmpty();

        restTemplate.delete("/test/bundle");

        assertThat(bundle.getEndpoints()).isEmpty();
    }

    public void bundleServicePostAddTest() {

        bundleServiceDeleteTest();

        // creating new bundle to add new endpoint to application bundle
        Bundle bundle = new Bundle();
        bundle.add(resources.createEndpoint("GET", "/pong", "200"));

        ResponseEntity<String> response = restTemplate.postForEntity(PATH_BUNDLE_ENDPOINT, bundle, String.class);

        // validating if endpoint was added correctly in bundle
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(this.bundle.getEndpoints().size()).isEqualTo(bundle.getEndpoints().size());
        assertThat(this.bundle.getEndpoints().get(0).getPath())
            .isEqualTo(bundle.getEndpoints().get(0).getPath());
    }

    @Test
    public void bundleServicePostUpdateTest() {

        bundleServicePostAddTest();

        // creating new bundle with an existing endpoint to be updated on Lyre Bundle.
        Bundle bundle = new Bundle();
        bundle.add(resources.createEndpoint("GET", "/pong", "404"));

        ResponseEntity<String> response = restTemplate.postForEntity(PATH_BUNDLE_ENDPOINT, bundle, String.class);

        // validating if endpoint was updated correctly in bundle
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(this.bundle.getEndpoints().size()).isEqualTo(bundle.getEndpoints().size());
        assertThat(this.bundle.getEndpoints().get(0).getResponse().getStatus())
            .isEqualTo(bundle.getEndpoints().get(0).getResponse().getStatus());
    }

    @Test
    public void bundleServicePostExceptionTest() {

        ResponseEntity<String> response = restTemplate.postForEntity(PATH_BUNDLE_ENDPOINT, new Bundle(), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void bundleServicePutTest() {

        // creating new bundle to take the place of the old bundle
        Bundle bundle = new Bundle();
        bundle.add(resources.createEndpoint("GET", "/ping", "200"));
        bundle.add(resources.createEndpoint("GET", "/pong", "404"));
        bundle.add(resources.createEndpoint("GET", "/ping-pong", "200"));
        bundle.add(resources.createEndpoint("GET", "/pong-ping", "404"));

        restTemplate.put(PATH_BUNDLE_ENDPOINT, bundle);

        assertThat(this.bundle.getEndpoints().size()).isEqualTo(bundle.getEndpoints().size());
        assertThat(this.bundle.getEndpoints().get(0).getPath())
            .isEqualTo(bundle.getEndpoints().get(0).getPath());
        assertThat(this.bundle.getEndpoints().get(2).getPath())
            .isEqualTo(bundle.getEndpoints().get(2).getPath());
    }

    @Test
    public void bundleServicePutExceptionTest() {

        bundleServiceDeleteTest();

        restTemplate.put(PATH_BUNDLE_ENDPOINT, new Bundle());

        assertThat(this.bundle.getEndpoints()).isEmpty();
    }


}
