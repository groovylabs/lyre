package com.github.groovylabs.lyre.test;

import com.github.groovylabs.lyre.domain.Endpoint;
import com.github.groovylabs.lyre.engine.reader.Reader;
import com.github.groovylabs.lyre.test.configurations.InterpreterConfiguration;
import com.github.groovylabs.lyre.test.configurations.LyrePropertiesConfiguration;
import com.github.groovylabs.lyre.test.configurations.ResourcesConfiguration;
import com.github.groovylabs.lyre.test.tools.Resources;
import com.github.groovylabs.lyre.test.tools.TempIO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Import({
    LyrePropertiesConfiguration.class,
    ResourcesConfiguration.class,
    InterpreterConfiguration.class
})
@TestPropertySource("classpath:application.properties")
public class InterpreterTest {

    @Autowired
    private Resources resources;

    @Autowired
    private Reader reader;

    @Test
    public void interpreterTest() {

        // new error-free endpoint
        String endpoint = resources.createEndpointAsYAML(
            "GET /path", "/endpoint", "POST",
            "application/json", "1000", "data_request",
            new String[]{"200", "application/xml", "data_response"}, new String[]{"1", "2", "3"});

        // write file on temporary directory
        File file = TempIO.buildFile("endpoint", ".lyre", resources.getDirectory(0));

        try {
            //write endpoint text into file
            TempIO.writeOnTempFile(file, endpoint);
        } catch (IOException e) {
            fail("Couldn't write on temporary file: " + e.getMessage());
        }

        //test interpreter and parser through reader.
        reader.read(file);

        // expecting lyre 1.0 syntax
        assertThat(reader.getInterpreter().getBundle()).isNotNull();
        assertThat(reader.getInterpreter().getBundle().getEndpoints()).isNotEmpty();

        Endpoint bundledEndpoint = reader.getInterpreter().getBundle().getEndpoints().get(0);

        //should have method
        assertThat(bundledEndpoint.getMethod())
            .isEqualTo(HttpMethod.POST);

        //should have path
        assertThat(bundledEndpoint.getPath())
            .isEqualTo("/path/endpoint");

        //should have consumes
        assertThat(bundledEndpoint.getConsumes())
            .isEqualTo("application/json");

        //should have data
        assertThat(bundledEndpoint.getData())
            .isEqualTo("data_request");

        //should have consumes
        assertThat(bundledEndpoint.getData())
            .isEqualTo("data_request");

        //TODO cookie

        //should have idle / timer
        assertThat(bundledEndpoint.getTimer().getIdle())
            .isEqualTo(1000);

        //should have response
        assertThat(bundledEndpoint.getResponse()).isNotNull();

        //should have status response
        assertThat(bundledEndpoint.getResponse().getStatus())
            .isEqualTo(HttpStatus.OK);

        //should have data response
        assertThat(bundledEndpoint.getResponse().getData())
            .isEqualTo("data_response");

        //should have produces response
        assertThat(bundledEndpoint.getResponse().getProduces())
            .isEqualTo("application/xml");

         //TODO configuration / setups

    }
}
