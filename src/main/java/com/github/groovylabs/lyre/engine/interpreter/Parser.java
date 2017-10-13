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

package com.github.groovylabs.lyre.engine.interpreter;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.groovylabs.lyre.domain.Endpoint;
import com.github.groovylabs.lyre.domain.appliers.Countdown;
import com.github.groovylabs.lyre.domain.enums.Level;
import com.github.groovylabs.lyre.domain.enums.Property;
import com.github.groovylabs.lyre.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.util.Map;

public abstract class Parser {

    protected static final Logger LOGGER = LoggerFactory.getLogger(Parser.class);

    @Autowired
    protected Validator validator;

    protected String fileName;

    protected void parse(Endpoint endpoint, Map.Entry<String, JsonNode> entry, Level level) {

        switch (level) {
            case ENDPOINT:

                endpoint.setFileName(fileName);

                // suported METHOD and PATH on key.
                String[] words = entry.getKey().split(" ", 2);

                // check if first word is method
                if (validator.check(words[0], "method"))
                    endpoint.setMethod(words[0]);

                // check if has second word and is path
                if (words.length > 1 && validator.check(words[1], "path"))
                    endpoint.setPath(words[1]);

                if (StringUtils.isEmpty(endpoint.getMethod()) && StringUtils.isEmpty(endpoint.getMethod()))
                    endpoint.setAlias(entry.getKey());
                else
                    endpoint.setAlias(endpoint.getMethod() + " " + endpoint.getPath());

                entry.getValue().fields().forEachRemaining(node ->
                    this.parse(endpoint, node, Level.REQUEST));

                break;
            case REQUEST:

                if (Property.METHOD.is(entry.getKey())) {

                    endpoint.setMethod(entry.getValue().asText());

                } else if (Property.PATH.is(entry.getKey())) {

                    endpoint.setPath(entry.getValue().asText());

                } else if (Property.ALIAS.is(entry.getKey()) || Property.NAME.is(entry.getKey())) {

                    endpoint.setAlias(entry.getValue().asText());

                } else if (Property.CONSUMES.is(entry.getKey())) {

                    endpoint.setConsumes(entry.getValue().asText());

                } else if (Property.IDLE.is(entry.getKey()) || Property.TIMEOUT.is(entry.getKey())) {

                    endpoint.getTimer().setIdle(entry.getValue().asLong(-1));

                } else if (Property.DATA.is(entry.getKey())) {

                    endpoint.setData(entry.getValue().asText());

                } else if (Property.RESPONSE.is(entry.getKey()) || Property.RESPONSES.is(entry.getKey())) {

                    entry.getValue().fields().forEachRemaining(node ->
                        this.parse(endpoint, node, Level.RESPONSE));

                } else if (Property.SETUP.is(entry.getKey())) {

                    entry.getValue().fields().forEachRemaining(node ->
                        this.parse(endpoint, node, Level.SETUP));

                } else {
                    unrecognizedElement(entry.getKey(), level);
                }

                break;
            case RESPONSE:

                if (Property.STATUS.is(entry.getKey())) {

                    endpoint.getResponse().setStatus(entry.getValue().asText());

                } else if (Property.PRODUCES.is(entry.getKey())) {

                    endpoint.getResponse().setProduces(entry.getValue().asText());

                } else if (Property.DATA.is(entry.getKey())) {

                    endpoint.getResponse().setData(entry.getValue().asText());

                } else {
                    unrecognizedElement(entry.getKey(), level);
                }

                break;
            case SETUP:

                if (Property.BUSY.is(entry.getKey())) {

                    // TODO factory to make Countdown
                    endpoint.getSetup().setCountdown(new Countdown(HttpStatus.TOO_MANY_REQUESTS, entry.getValue().asLong(-1)));

                } else if (Property.BROKEN.is(entry.getKey())) {

                    // TODO factory to make Countdown
                    endpoint.getSetup().setCountdown(new Countdown(HttpStatus.INTERNAL_SERVER_ERROR, entry.getValue().asLong(-1)));

                } else if (Property.FORBIDDEN.is(entry.getKey())) {

                    // TODO factory to make Countdown
                    endpoint.getSetup().setCountdown(new Countdown(HttpStatus.FORBIDDEN, entry.getValue().asLong(-1)));

                } else {
                    unrecognizedElement(entry.getKey(), level);
                }

            case PARAMETER:
                break;

        }

    }

    private void unrecognizedElement(String key, Level level) {
        LOGGER.warn("Unrecognized element [{}] on [{}] level, inside file: [{}]", key, level, fileName);
    }

}
