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

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.groovylabs.lyre.domain.Endpoint;
import com.github.groovylabs.lyre.engine.reader.Reader;
import com.github.groovylabs.lyre.test.configurations.ReaderConfiguration;
import com.github.groovylabs.lyre.test.tools.Resources;
import com.github.groovylabs.lyre.test.tools.TempIO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Import({ReaderConfiguration.class})
public class ReaderTest {

    @Autowired
    private Resources resources;

    @Autowired
    private Reader reader;

    @Test
    public void readJSONFile() {

        // new error-free endpoint
        Endpoint endpoint = resources.createEndpoint("GET", "/test-json-file", "200");

        // write json file on temporary directory
        ObjectMapper mapperJson = new ObjectMapper(new JsonFactory());
        File file = TempIO.buildFile("read-json-file", ".lyre", resources.getDirectory(0));

        try {
            mapperJson.writeValue(file, endpoint);
        } catch (IOException e) {
            fail("Couldn't write on temporary file: " + e.getMessage());
        }

        reader.read(file);

        //should have a object node with json file on it.
        assertThat(reader.getObjectNodes()).isNotEmpty();
        assertThat(reader.getObjectNodes().get(file.getAbsolutePath())).isNotNull();

    }

    @Test
    public void readYAMLFile() {

        // new error-free endpoint
        Endpoint endpoint = resources.createEndpoint("GET", "/test-yaml-file", "200");

        // write yaml file on temporary directory
        ObjectMapper mapperJson = new ObjectMapper(new YAMLFactory());
        File file = TempIO.buildFile("read-yaml-file", ".lyre", resources.getDirectory(0));

        try {
            mapperJson.writeValue(file, endpoint);
        } catch (IOException e) {
            fail("Couldn't write on temporary file: " + e.getMessage());
        }

        reader.read(file);

        //should have a object node with yaml file on it.
        assertThat(reader.getObjectNodes()).isNotEmpty();
        assertThat(reader.getObjectNodes().get(file.getAbsolutePath())).isNotNull();

    }

}
