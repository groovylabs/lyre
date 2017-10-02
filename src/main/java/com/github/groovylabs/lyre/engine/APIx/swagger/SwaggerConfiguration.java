package com.github.groovylabs.lyre.engine.APIx.swagger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@ConditionalOnProperty(prefix = "lyre.ui.swagger", name = "enabled", havingValue = "true", matchIfMissing = true)
@Import({Swagger2DocumentationConfiguration.class})
public class SwaggerConfiguration {

    @Component
    @Primary
    public class CombinedSwaggerResourcesProvider implements SwaggerResourcesProvider {

        @Override
        public List<SwaggerResource> get() {

            SwaggerResource jerseySwaggerResource = new SwaggerResource();
            jerseySwaggerResource.setLocation("/api/docs");
            jerseySwaggerResource.setSwaggerVersion("2.0");
            jerseySwaggerResource.setName("Lyre REST API Mock");

            return Stream.of(jerseySwaggerResource).collect(Collectors.toList());
        }

    }

}
