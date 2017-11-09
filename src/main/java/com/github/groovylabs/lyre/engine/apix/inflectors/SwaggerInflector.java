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

package com.github.groovylabs.lyre.engine.apix.inflectors;

import io.swagger.config.FilterFactory;
import io.swagger.core.filter.SwaggerSpecFilter;
import io.swagger.inflector.utils.VendorSpecFilter;
import io.swagger.models.Swagger;
import org.glassfish.jersey.process.Inflector;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

public class SwaggerInflector implements Inflector<ContainerRequestContext, Response> {

    private Swagger swagger;

    public SwaggerInflector(Swagger swagger) {
        this.swagger = swagger;
    }

    @Override
    public Response apply(ContainerRequestContext arg0) {
        SwaggerSpecFilter filter = FilterFactory.getFilter();
        if (filter != null) {

            Map<String, Cookie> cookiesValue = arg0.getCookies();
            Map<String, String> cookies = new HashMap<>();
            if (cookiesValue != null) {
                for (Map.Entry<String, Cookie> map : cookiesValue.entrySet()) {
                    cookies.put(map.getKey(), cookiesValue.get(map.getKey()).getValue());
                }
            }

            MultivaluedMap<String, String> headers = arg0.getHeaders();
            return Response.ok().entity(new VendorSpecFilter().filter(getSwagger(), filter, null, cookies, headers)).build();
        }
        return Response.ok().entity(getSwagger()).build();
    }

    private Swagger getSwagger() {
        return swagger;
    }

}
