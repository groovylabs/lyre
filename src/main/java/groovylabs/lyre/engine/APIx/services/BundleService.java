package groovylabs.lyre.engine.APIx.services;

import groovylabs.lyre.engine.APIx.APIx;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path(value = "/bundle")
public class BundleService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response message() {
        return Response.ok().entity(APIx.bundle).build();
    }

}
