package com.github.groovylabs.lyre.engine.APIx.services;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Component
@Path("")
public class LandingPageService {

    @GET
    public Response landingPageView() {
        return Response.status(Response.Status.OK)
            .entity(getClass().getResourceAsStream("/static/landing-page.html"))
            .build();
    }

}
