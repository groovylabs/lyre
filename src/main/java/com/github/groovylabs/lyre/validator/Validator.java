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

package com.github.groovylabs.lyre.validator;

import com.github.groovylabs.lyre.domain.Endpoint;
import com.github.groovylabs.lyre.domain.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(Validator.class);

    /**
     * Responsible to check the integrity of endpoint before insert it into list and
     * verify if it's already been inserted.
     */

    public boolean integrity(String fileName, Endpoint endpoint, List<Endpoint> savedEndpoints, boolean updatable) {

        if (StringUtils.isEmpty(endpoint.getMethod()) || StringUtils.isEmpty(endpoint.getPath())
            || StringUtils.isEmpty(endpoint.getResponse().getStatus())) {

            LOGGER.warn("Dropping endpoint: [method:{}, path:{} found in file: [{}]]. " +
                    "Reason: This endpoint does not have minimum required information (method, path, response)",
                endpoint.getMethod(), endpoint.getPath(), fileName);

            return false;
        }

        if (!StringUtils.isEmpty(endpoint.getData()) &&
            (!endpoint.getMethod().equals(HttpMethod.POST) && !endpoint.getMethod().equals(HttpMethod.PUT))) {
            LOGGER.warn("Method [{} - {}] does not support request body. Lyre will ignore this property.",
                endpoint.getMethod(), endpoint.getPath());
            endpoint.setData(null);
        }

        if (updatable)
            savedEndpoints.removeIf(itEndpoint -> itEndpoint.getMethod().equals(endpoint.getMethod()) &&
                itEndpoint.getPath().equals(endpoint.getPath()));

        if (savedEndpoints.isEmpty())
            return true;

        for (Endpoint savedEndpoint : savedEndpoints) {
            if (savedEndpoint.getMethod().equals(endpoint.getMethod()) &&
                savedEndpoint.getPath().equals(endpoint.getPath())) {

                LOGGER.warn("Skipping endpoint: [{} {} found in file: [{}]]. " +
                        "Reason: This endpoint already exists in file [{}]",
                    endpoint.getMethod(), endpoint.getPath(), fileName, savedEndpoint.getFileName());

                return false;
            }
        }

        return true;
    }

    public boolean check(Endpoint endpoint) {

        return !(endpoint == null ||
            endpoint.getResponse() == null ||
            StringUtils.isEmpty(endpoint.getPath()) ||
            StringUtils.isEmpty(endpoint.getMethod()) ||
            StringUtils.isEmpty(endpoint.getResponse().getStatus()));

    }

    public String containsParameters(String path, Parameter parameter) {

        if (path.contains(Parameter.START_QUERY_PARAM)) {
            Pattern.compile("\\?\\w*?")
                .splitAsStream(path)
                .filter(s -> s.contains(Parameter.EQUAL_QUERY_PARAM))
                .forEach(word -> parameter.composeQueryParam(word));

            // return first
            return path.split("\\?")[0];
        } else {
            return path;
        }
    }

    public boolean check(String value, String reference) {

        if (reference.equals("path"))
            return !StringUtils.isEmpty(value);
        else if (reference.equals("method"))
            return HttpMethod.resolve(value) != null;

        return false;
    }
}
