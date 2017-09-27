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
