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

package com.github.groovylabs.lyre.test.tools;

import com.github.groovylabs.lyre.domain.Endpoint;
import com.github.groovylabs.lyre.domain.Response;
import org.springframework.boot.test.context.TestComponent;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

@TestComponent
public class Resources {

    private File[] directories;

    public Resources() {
        try {
            // creating temp directory to create temporary files on it.
            directories = TempIO.createTempDirectories();

            assertThat(directories).isNotNull();
            Arrays.stream(directories).forEach(dir -> assertThat(dir).isNotNull());

        } catch (IOException e) {
            fail("Couldn't create temporary directories: " + e.getMessage());
        }


    }

    public Endpoint createEndpoint(String method, String path, String status) {

        // new error-free endpoint
        Endpoint endpoint = new Endpoint();
        endpoint.setMethod(method);
        endpoint.setPath(path);
        endpoint.setResponse(new Response());
        endpoint.getResponse().setStatus(status);

        return endpoint;
    }

    public String createEndpointAsYAML(
        String key, String path, String method, String alias, String consumes, String idle,
        String data, String[] response, String[] setup) {

        String endpoint = "";
        endpoint += key + ":\n";
        endpoint += "    path: " + path + "\n";
        endpoint += "    method: " + method + "\n";
        endpoint += "    alias: " + alias + "\n";
        endpoint += "    consumes: " + consumes + "\n";
        endpoint += "    idle: " + idle + "\n";
        endpoint += "    data: " + data + "\n";

        if (response.length == 3) {
            endpoint += "    response:\n";

            endpoint += "        status: " + response[0] + "\n";
            endpoint += "        produces: " + response[1] + "\n";
            endpoint += "        data: " + response[2] + "\n";
        }

        if (setup.length == 3) {
            endpoint += "    setup:\n";

            endpoint += "        busy: " + setup[0] + "\n";
            endpoint += "        broken: " + setup[1] + "\n";
            endpoint += "        forbidden: " + setup[2] + "\n";
        }


        return endpoint;
    }

    public File getDirectory(int level) {
        return (level >= 0 && level < directories.length) ? this.getDirectories()[level] : null;
    }

    public File[] getDirectories() {
        return directories;
    }

    public void setDirectories(File[] directories) {
        this.directories = directories;
    }
}
