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

package com.github.groovylabs.lyre.engine.reader;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.groovylabs.lyre.config.LyreProperties;
import com.github.groovylabs.lyre.engine.interpreter.Interpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class Reader {

    private static final Logger LOGGER = LoggerFactory.getLogger(Reader.class);

    @Autowired
    private LyreProperties lyreProperties;

    @Autowired
    private Interpreter interpreter;

    private Map<String, ObjectNode> objectNodes;

    public void read(File... files) {

        objectNodes = new HashMap<>();

        LOGGER.info("Reading files, supported syntax: [.yml] or [.json]");

        for (File file : files)
            readFile(file, objectNodes);

        interpreter.interpret(objectNodes);
    }

    private void readFile(File file, Map<String, ObjectNode> objectNodes) {

        try {

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

            objectNodes.put(file.getPath(), mapper.readValue(file, ObjectNode.class));

        } catch (JsonParseException | JsonMappingException fileMapperException) {

            try {

                ObjectMapper mapperJson = new ObjectMapper(new JsonFactory());

                objectNodes.put(file.getPath(), mapperJson.readValue(file, ObjectNode.class));

            } catch (JsonParseException | JsonMappingException e) {

                LOGGER.error("Error mapping file [{}], invalid .yml and .json format", file.getName());

                if (lyreProperties.isDebug()) {
                    LOGGER.debug("Stacktrace", e);
                } else
                    LOGGER.warn("\u21B3 " + "Enable debug mode to see stacktrace log");

            } catch (IOException e) {

                LOGGER.error("Error reading file [{}]", file.getName());

                if (lyreProperties.isDebug()) {
                    LOGGER.debug("Stacktrace", e);
                } else
                    LOGGER.warn("\u21B3 " + "Enable debug mode to see stacktrace log");
            }

        } catch (IOException e) {

            LOGGER.error("Error reading file [{}]", file.getName());

            if (lyreProperties.isDebug()) {
                LOGGER.debug("Stacktrace", e);
            } else
                LOGGER.warn("\u21B3 " + "Enable debug mode to see stacktrace log");

        }
    }

    public LyreProperties getLyreProperties() {
        return lyreProperties;
    }

    public void setLyreProperties(LyreProperties lyreProperties) {
        this.lyreProperties = lyreProperties;
    }

    public Interpreter getInterpreter() {
        return interpreter;
    }

    public void setInterpreter(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    public Map<String, ObjectNode> getObjectNodes() {
        return objectNodes;
    }

    public void setObjectNodes(Map<String, ObjectNode> objectNodes) {
        this.objectNodes = objectNodes;
    }
}
