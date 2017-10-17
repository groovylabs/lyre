/*
 * MIT License
 *
 * Copyright (c) 2017 Groovylabs
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.github.groovylabs.lyre.test;

import com.github.groovylabs.lyre.domain.Endpoint;
import com.github.groovylabs.lyre.engine.reader.Reader;
import com.github.groovylabs.lyre.test.configurations.InterpreterConfiguration;
import com.github.groovylabs.lyre.test.tools.Resources;
import com.github.groovylabs.lyre.test.tools.TempIO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Import({InterpreterConfiguration.class})
public class InterpreterTest {

    @Autowired
    private Resources resources;

    @Autowired
    private Reader reader;

    @Test
    public void interpreterTest() {

        // new error-free endpoint
        String endpoint = resources.createEndpointAsYAML(
            "GET", "/path/endpoint", "POST", "endpoint test",
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

        //should have alias/name
        assertThat(bundledEndpoint.getAlias())
            .isEqualTo("endpoint test");

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
        assertThat(bundledEndpoint.getProperty().getTimer().getIdle())
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
