package com.github.groovylabs.lyre.engine.APIx.inflectors;

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

            Map<String, Cookie> cookiesvalue = arg0.getCookies();
            Map<String, String> cookies = new HashMap<>();
            if (cookiesvalue != null) {
                for (String key : cookiesvalue.keySet()) {
                    cookies.put(key, cookiesvalue.get(key).getValue());
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
