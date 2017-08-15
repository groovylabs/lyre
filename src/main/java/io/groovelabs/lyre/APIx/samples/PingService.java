package io.groovelabs.lyre.APIx.samples;


import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Component
@Path(value = "/ping")
public class PingService {

    @GET
    public String message() {
        return "pong";
    }

}
