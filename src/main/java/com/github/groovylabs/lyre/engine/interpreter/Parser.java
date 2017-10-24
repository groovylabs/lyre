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
import com.github.groovylabs.lyre.domain.Level;
import com.github.groovylabs.lyre.domain.Syntax;
import com.github.groovylabs.lyre.domain.errors.SyntaxError;
import com.github.groovylabs.lyre.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Map;

public abstract class Parser {

    protected static final Logger LOGGER = LoggerFactory.getLogger(Parser.class);

    @Autowired
    protected Validator validator;

    protected String fileName;

    protected void parse(Endpoint endpoint, Map.Entry<String, JsonNode> entry, Level... tree) {

        Level level = tree[tree.length-1];

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
                    endpoint.setPath(!words[1].startsWith("/") ? "/" + words[1] : words[1]);

                if (StringUtils.isEmpty(endpoint.getMethod()) && StringUtils.isEmpty(endpoint.getPath()))
                    endpoint.setAlias(entry.getKey());
                else
                    endpoint.setAlias(endpoint.getMethod() + " " + endpoint.getPath());

                entry.getValue().fields().forEachRemaining(node ->
                    this.parse(endpoint, node, Level.REQUEST));

                break;
            case HEADER:

                try {
                    tree[0].has(Syntax.HEADER, entry.getKey()).apply(endpoint, entry.getValue().asText());
                } catch (SyntaxError error) {
                    unrecognizedElement(entry.getKey(), level);
                }

                break;
            case REQUEST:

                if (Syntax.HEADER.is(entry.getKey())) {

                    System.out.println(level);
                    System.out.println(entry.getKey());

                    entry.getValue().fields().forEachRemaining(node ->
                        this.parse(endpoint, node, Level.REQUEST, Level.HEADER));

                } else if (Syntax.RESPONSE.is(entry.getKey()) || Syntax.RESPONSES.is(entry.getKey())) {

                    entry.getValue().fields().forEachRemaining(node ->
                        this.parse(endpoint, node, Level.RESPONSE));

                } else if (Syntax.PROPERTY.is(entry.getKey()) || Syntax.PROPERTIES.is(entry.getKey())) {

                    entry.getValue().fields().forEachRemaining(node ->
                        this.parse(endpoint, node, Level.PROPERTY));

                }

                try {
                    level.has(entry.getKey()).apply(endpoint, entry.getValue().asText());
                } catch (SyntaxError error) {
                    unrecognizedElement(entry.getKey(), level);
                }

                break;
            case RESPONSE:

                if (Syntax.HEADER.is(entry.getKey())) {

                    entry.getValue().fields().forEachRemaining(node ->
                        this.parse(endpoint, node, Level.RESPONSE, Level.HEADER));

                }

            case PROPERTY:

                try {
                    level.has(entry.getKey()).apply(endpoint, entry.getValue().asText());
                } catch (SyntaxError error) {
                    unrecognizedElement(entry.getKey(), level);
                }

                break;
        }

    }

    private void unrecognizedElement(String key, Level level) {
        LOGGER.warn("Unrecognized element [{}] on [{}] level, inside file: [{}]", key, level, fileName);
    }

}
