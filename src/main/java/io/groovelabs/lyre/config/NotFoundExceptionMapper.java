package io.groovelabs.lyre.config;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.io.InputStream;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    public Response toResponse(NotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)
            .entity(htmlFile())
            .build();
    }

    private InputStream htmlFile() {
        return getClass().getResourceAsStream("/static/404.html");
        /*File htmlError = new File(ScannerProperties.path + "/static/404.html");
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(htmlError);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return inputStream;*/
    }
}
