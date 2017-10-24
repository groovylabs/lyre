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

package com.github.groovylabs.lyre.engine.APIx.swagger.resources;

import com.github.groovylabs.lyre.domain.Endpoint;
import io.swagger.models.*;
import io.swagger.models.parameters.QueryParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Component
public class SwaggerHelper {

    public void buildSwaggerApiPath(Swagger swagger, Endpoint endpoint) {

        Operation operation = new Operation();
        operation.consumes(endpoint.getConsumes());
        io.swagger.models.Response response = new io.swagger.models.Response();

        if (!StringUtils.isEmpty(endpoint.getData()))
            buildSwaggerApiBodyParam(operation);

        response.setDescription(endpoint.getResponse().getStatus().getReasonPhrase());

        operation.addResponse(endpoint.getResponse().getStatus().toString(), response);

        swagger.path(endpoint.getPath(), new Path().set(endpoint.getMethod().toString().toLowerCase(), operation));

    }

    public void buildSwaggerApiBodyParam(Operation operation) {
        io.swagger.models.Response response = new io.swagger.models.Response();
        ParameterInterfaceImpl param = new ParameterInterfaceImpl();

        param.setName("Body");
        param.setIn("body");
        param.setRequired(true);
        // TODO review
        param.setDescription("Expected object in lyre file by the endpoint");

        response.setDescription(Response.Status.NOT_ACCEPTABLE.toString());

        operation.addParameter(param);
        operation.addResponse(String.valueOf(Response.Status.NOT_ACCEPTABLE.getStatusCode()), response);
    }

    public void buildSwaggerManagement(Swagger swagger) {

        List<Tag> tags = new ArrayList<>();

        Tag endpoint = new Tag()
            .name("1 : Endpoint Management");

        Tag bundle = new Tag()
            .name("2 : Bundle Management");

        Tag configuration = new Tag()
            .name("3 : Server Configuration");

        Tag monitor = new Tag()
            .name("4 : Monitoring");

        Tag danger = new Tag()
            .name("5 : Danger Zone");

        tags.add(endpoint);
        tags.add(bundle);
        tags.add(configuration);
        tags.add(monitor);
        tags.add(danger);


        swagger.setTags(tags);

        //Endpoints Services
        buildEndpointServices(swagger);

        // Bundle Services
        buildBundleServices(swagger);
    }

    private void buildEndpointServices(Swagger swagger) {

        // GET
        Operation get = new Operation();
        get.addParameter(new QueryParameter().name("method"));
        get.addResponse("200", resp().description("Requested endpoint"));
        get.addResponse("404", resp().description("Endpoint not found"));
        get.tag("1 : Endpoint Management");

        swagger.path("/endpoint", new Path()
            .set("get", get));

    }

    private void buildBundleServices(Swagger swagger) {

        // GET
        Operation get = new Operation();
        get.addResponse("200", resp().description("Bundle of endpoints"));
        get.addResponse("204", resp().description("Empty bundle"));
        get.tag("1 : Manage Bundle");

        // DELETE
        Operation delete = new Operation();
        delete.addResponse("200", resp().description("Attempting to clear bundle"));
        delete.tag("2 : Danger Zone");

        // POST
        Operation post = new Operation();
        post.addResponse("200", resp().description("Add new endpoints or update them"));
        post.addResponse("400", resp().description("Malformed bundle entity"));
        post.tag("1 : Manage Bundle");

        // PUT
        Operation put = new Operation();
        put.addResponse("200", resp().description("Add a new bundle, will override previous bundle."));
        put.addResponse("400", resp().description("Malformed bundle entity"));
        put.tag("2 : Danger Zone");

        swagger.path("/bundle", new Path()
            .set("delete", delete)
            .set("get", get)
            .set("post", post)
            .set("put", put));

    }

    private io.swagger.models.Response resp() {
        return new io.swagger.models.Response();
    }


}
