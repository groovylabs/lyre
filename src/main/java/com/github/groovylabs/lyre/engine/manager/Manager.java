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

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.groovylabs.lyre.config.LyreProperties;
import com.github.groovylabs.lyre.domain.Bundle;
import com.github.groovylabs.lyre.domain.Pack;
import com.github.groovylabs.lyre.domain.interfaces.PersistOn;
import com.github.groovylabs.lyre.domain.interfaces.Update;
import com.github.groovylabs.lyre.engine.apix.controller.APIxController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Component
public class Manager implements PersistOn<Pack>, Update<Bundle> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Manager.class);

    private LyreProperties lyreProperties;

    private APIxController apixController;

    private File packFile;

    private Pack pack;

    private Bundle bundle;

    @Autowired
    public Manager(LyreProperties lyreProperties, APIxController apixController, Bundle bundle) {
        this.lyreProperties = lyreProperties;
        this.apixController = apixController;
        this.bundle = bundle;
    }

    @PostConstruct
    private void construct() {
        try {
            touchFile();

            pack = parsePack();

        } catch (IOException e) {
            this.noticeFallback(e);
        }
    }

    public void handle(Bundle bundle, String attemptSource) {

        if (this.update(bundle)) {
            if (this.persist(pack)) {
                this.bundle.clone(pack.getBundle());
            } else {
                LOGGER.info("Using in-memory bundle control only, pack unsynchronized.");
            }
        }

        apixController.bootAttempt(attemptSource);
    }

    public boolean persist(Pack pack) {

        try {
            if (packFile != null) {
                ObjectMapper mapper = new ObjectMapper(new JsonFactory());
                mapper.writeValue(packFile, pack);
                return true;
            }
        } catch (IOException e) {
            LOGGER.error("Error while writing into auto-generated pack file, please check read/write permissions on path: {}",
                lyreProperties.getBundleFilePath());
        }

        return false;
    }

    public boolean update(Bundle bundle) {

        if (!bundle.isEmpty()) {
            bundle.getEndpoints().forEach(endpoint -> {

                if (pack.contains(endpoint)) {
                    if (endpoint.getLastModified() > pack.modifiedAt(endpoint)) {
                        pack.getBundle().update(endpoint);
                        pack.getMetadata().modifiedAt(endpoint);
                    }
                } else {
                    pack.getBundle().add(endpoint);
                    pack.getMetadata().modifiedAt(endpoint);
                }
            });
            return true;
        } else
            return false;
    }

    private Pack parsePack() throws IOException {
        try {

            ObjectMapper mapper = new ObjectMapper(new JsonFactory());

            return mapper.readValue(packFile, Pack.class);
        } catch (JsonMappingException e) {
            if (e.getMessage().startsWith("No content to map due to end-of-input")) {
                LOGGER.info("Found empty bundle on auto-generated pack file");
                return new Pack();
            } else {
                LOGGER.error("Error while parsing auto-generated pack file");
                throw e;
            }
        } catch (IOException e) {
            LOGGER.error("Error while read auto-generated pack file, please check if file is a valid YAML on path: {}",
                lyreProperties.getBundleFilePath());
            throw e;
        }
    }

    private void touchFile() throws IOException {
        try {

            packFile = new File(lyreProperties.getBundleFilePath());

            if (!packFile.exists() && !packFile.createNewFile()) {
                LOGGER.info("Lyre auto-generated pack file already exists.");
            }
        } catch (IOException e) {
            LOGGER.error("Error while creating auto-generated pack file, please check read/write permissions on path: {}",
                lyreProperties.getBundleFilePath());
            throw e;
        }
    }

    private void noticeFallback(Exception e) {
        LOGGER.error("\u21B3 " + "Falling back to in-memory bundle control");

        if (lyreProperties.isDebug()) {
            LOGGER.error("Stacktrace", e);
        } else
            LOGGER.warn("\u21B3 " + "Enable debug mode to see stacktrace log");

    }
}
