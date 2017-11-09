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

package com.github.groovylabs.lyre.engine.manager;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.groovylabs.lyre.config.LyreProperties;
import com.github.groovylabs.lyre.domain.Bundle;
import com.github.groovylabs.lyre.domain.ManagedBundle;
import com.github.groovylabs.lyre.engine.apix.controller.APIxController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Component
public class Manager implements Management<Bundle> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Manager.class);

    private LyreProperties lyreProperties;

    private APIxController apixController;

    private File bundleFile;

    private ManagedBundle managedBundle;

    @Autowired
    public Manager(LyreProperties lyreProperties, APIxController apixController) {
        this.lyreProperties = lyreProperties;
        this.apixController = apixController;
    }

    @PostConstruct
    private void construct() {
        try {
            touchBundleFile();

            managedBundle = parseBundleFile();

        } catch (IOException e) {
            LOGGER.error("\u21B3 " + "Falling back to in-memory bundle control");

            if (lyreProperties.isDebug()) {
                LOGGER.error("Stacktrace", e);
            } else
                LOGGER.warn("\u21B3 " + "Enable debug mode to see stacktrace log");
        }
    }

    public void handle(Bundle bundle) {

        try {
            this.persist(bundle);
        } catch (IOException e) {
             LOGGER.info("Error while writing ");
        }
        apixController.bootAttempt("managing file resource(s)");

    }

    public boolean persist(Bundle bundle) throws IOException {

        try {
            if (bundleFile != null) {
                ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
                mapper.writeValue(bundleFile, bundle);
            }
        } catch (IOException e) {
            LOGGER.error("Error while writing into auto-generated file, please check read/write permissions on path: {}",
                lyreProperties.getBundleFilePath());
            throw e;
        }

        return false;
    }

    public boolean update(Bundle bundle) {
        return true;
    }

    private ManagedBundle parseBundleFile() throws IOException {
        try {

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

            return mapper.readValue(bundleFile, ManagedBundle.class);
        } catch (JsonMappingException e) {
            if (e.getMessage().startsWith("No content to map due to end-of-input")) {
                LOGGER.info("Found empty bundle on auto-generated file");
                return new ManagedBundle();
            } else {
                LOGGER.error("Error while parsing auto-generated file");
                throw e;
            }
        } catch (IOException e) {
            LOGGER.error("Error while read auto-generated file, please check if file is a valid YAML on path: {}",
                lyreProperties.getBundleFilePath());
            throw e;
        }
    }

    private void touchBundleFile() throws IOException {
        try {

            bundleFile = new File(lyreProperties.getBundleFilePath());

            if (!bundleFile.exists() && !bundleFile.createNewFile()) {
                LOGGER.info("Lyre auto-generated file already exists.");
            }
        } catch (IOException e) {
            LOGGER.error("Error while creating auto-generated file, please check read/write permissions on path: {}",
                lyreProperties.getBundleFilePath());
            throw e;
        }
    }

}
