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

package com.github.groovylabs.lyre.engine.apix.swagger.resources;

import com.github.groovylabs.lyre.domain.Endpoint;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.models.Tag;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.QueryParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.ws.rs.core.Response;

@Component
public class SwaggerHelper {

    private static final String ENDPOINT_MANAGEMENT = "1 : Endpoint Management";
    private static final String BUNDLE_MANAGEMENT = "2 : Bundle Management";
    private static final String SERVER_CONFIGURATION = "3 : Server Configuration";
    private static final String MONITORING = "4 : Monitoring";
    private static final String DANGER_ZONE = "5 : Danger Zone";

    private static final String TYPE_STRING = "string";

    public void buildSwaggerApiPath(Swagger swagger, Endpoint endpoint) {

        Operation operation = new Operation();
        operation.tag("API");
        operation.consumes(endpoint.getConsumes());
        io.swagger.models.Response response = new io.swagger.models.Response();

        if (!StringUtils.isEmpty(endpoint.getData()))
            buildSwaggerApiBodyParam(operation);

        response.setDescription(endpoint.getResponse().getStatus().getReasonPhrase());

        operation.addResponse(endpoint.getResponse().getStatus().toString(), response);

        swagger.path(endpoint.getPath(), new Path().set(endpoint.getMethod().toString().toLowerCase(), operation));

    }

    private void buildSwaggerApiBodyParam(Operation operation) {
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

        swagger.getTags().add(new Tag().name(ENDPOINT_MANAGEMENT));
        swagger.getTags().add(new Tag().name(BUNDLE_MANAGEMENT));
        swagger.getTags().add(new Tag().name(SERVER_CONFIGURATION));
        swagger.getTags().add(new Tag().name(MONITORING));
        swagger.getTags().add(new Tag().name(DANGER_ZONE));

        //Endpoints Services
        buildEndpointServices(swagger);

        // Bundle Services
        buildBundleServices(swagger);
    }

    private void buildEndpointServices(Swagger swagger) {

        BodyParameter bodyParameter = new BodyParameter().name("Body").description("Endpoint entity");
        bodyParameter.setRequired(true);

        // GET
        Operation get = new Operation();
        get.addParameter(new QueryParameter().name("method").required(true).description("Endpoint method").type(TYPE_STRING));
        get.addParameter(new QueryParameter().name("path").required(true).description("Endpoint path").type(TYPE_STRING));
        get.addResponse("200", resp().description("Requested endpoint"));
        get.addResponse("400", resp().description("Malformed parameters"));
        get.addResponse("404", resp().description("Bundle is empty"));
        get.addResponse("404", resp().description("Endpoint does not exist"));
        get.tag(ENDPOINT_MANAGEMENT);

        //DELETE
        Operation delete = new Operation();
        delete.addParameter(new QueryParameter().name("method").required(true).description("Endpoint method").type(TYPE_STRING));
        delete.addParameter(new QueryParameter().name("path").required(true).description("Endpoint path").type(TYPE_STRING));
        delete.addResponse("200", resp().description("Attempting to delete endpoint"));
        delete.addResponse("400", resp().description("Malformed parameters"));
        delete.tag(DANGER_ZONE);

        //POST
        Operation post = new Operation();
        post.addParameter(bodyParameter);
        post.addResponse("200", resp().description("Update a existing endpoint"));
        post.addResponse("400", resp().description("Malformed endpoint entity"));
        post.addResponse("404", resp().description("Endpoint does not exist"));
        post.tag(ENDPOINT_MANAGEMENT);

        //PUT
        Operation put = new Operation();
        put.addParameter(bodyParameter);
        put.addResponse("200", resp().description("Add new endpoint or update it"));
        put.addResponse("400", resp().description("Malformed endpoint entity"));
        put.tag(ENDPOINT_MANAGEMENT);

        swagger.path("/endpoint", new Path()
            .set("delete", delete)
            .set("get", get)
            .set("post", post)
            .set("put", put));

    }

    private void buildBundleServices(Swagger swagger) {

        // GET
        Operation get = new Operation();
        get.addResponse("200", resp().description("Bundle of endpoints"));
        get.addResponse("204", resp().description("Empty bundle"));
        get.tag(BUNDLE_MANAGEMENT);

        // DELETE
        Operation delete = new Operation();
        delete.addResponse("200", resp().description("Attempting to delete bundle"));
        delete.tag(DANGER_ZONE);

        // POST
        Operation post = new Operation();
        post.addResponse("200", resp().description("Add new endpoints or update them"));
        post.addResponse("400", resp().description("Malformed bundle entity"));
        post.tag(BUNDLE_MANAGEMENT);

        // PUT
        Operation put = new Operation();
        put.addResponse("200", resp().description("Add a new bundle, will override previous bundle."));
        put.addResponse("400", resp().description("Malformed bundle entity"));
        put.tag(DANGER_ZONE);

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
