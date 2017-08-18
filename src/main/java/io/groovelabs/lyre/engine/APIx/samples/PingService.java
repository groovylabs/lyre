package io.groovelabs.lyre.engine.APIx.samples;


import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Component
@Path(value = "/ping")
public class PingService {

    @GET
    @Consumes()
    public String message() {
        return "pong";
    }

}
