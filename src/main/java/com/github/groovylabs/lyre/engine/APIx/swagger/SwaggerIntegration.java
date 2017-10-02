package com.github.groovylabs.lyre.engine.APIx.swagger;

import com.github.groovylabs.lyre.config.LyreProperties;
import com.github.groovylabs.lyre.domain.Bundle;
import com.github.groovylabs.lyre.domain.Endpoint;
import io.swagger.config.FilterFactory;
import io.swagger.core.filter.SwaggerSpecFilter;
import io.swagger.inflector.utils.VendorSpecFilter;
import io.swagger.models.Info;
import io.swagger.models.License;
import io.swagger.models.Scheme;
import io.swagger.models.Swagger;
import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class SwaggerIntegration {

    @Autowired
    private LyreProperties lyreProperties;

    public Swagger buildSwagger(Bundle bundle) {

        // ref https://github.com/OAI/OpenAPI-Specification/tree/master/examples/v2.0/json

        Swagger swagger = new Swagger();
        swagger.setInfo(swaggerInfo());
        swagger.setBasePath(lyreProperties.getApiPath());
        swagger.setSchemes(Stream.of(Scheme.HTTP).collect(Collectors.toList()));
        swagger.setConsumes(Stream.of("application/json").collect(Collectors.toList()));
        swagger.setProduces(Stream.of("application/json").collect(Collectors.toList()));

        //TODO add paths

        for (Endpoint endpoint : bundle.getEndpoints()) {


        }

        return swagger;
    }

    public void enableSwagger(Bundle bundle, ResourceConfig resourceConfig) {

        Swagger swagger = buildSwagger(bundle);

        final Resource.Builder json = Resource.builder();
        json.path("/docs")
            .addMethod(HttpMethod.GET)
            .produces(MediaType.APPLICATION_JSON)
            .handledBy(new SwaggerResourceController(swagger))
            .build();

        resourceConfig.registerResources(json.build());
    }

    private class SwaggerResourceController implements Inflector<ContainerRequestContext, Response> {

        private Swagger swagger;

        public SwaggerResourceController(Swagger swagger) {
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

    private final Info swaggerInfo() {

        Info info = new Info();

        info.version("1.0.0");
        info.title("Lyre Swagger UI");
        info.description("A Mock API exposed to call Lyre REST endpoints.");

        //TODO contact info

        License license = new License();
        license.name("MIT");
        license.setUrl("https://github.com/groovylabs/lyre/blob/master/LICENSE");

        info.license(new License());

        return info;
    }

}
