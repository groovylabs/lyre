package com.github.groovylabs.lyre.validator;

import com.github.groovylabs.lyre.config.LyreProperties;
import com.github.groovylabs.lyre.domain.Endpoint;
import org.glassfish.jersey.message.internal.ReaderWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.ws.rs.container.ContainerRequestContext;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(Validator.class);

    @Autowired
    private LyreProperties lyreProperties;

    /**
     * Responsible to check the integrity of endpoint before insert it into list and
     * verify if it's already been inserted.
     */

    public boolean integrity(String fileName, Endpoint endpoint, List<Endpoint> savedEndpoints, boolean updatable) {

        if (!endpoint.getMethod().equals(HttpMethod.POST) && !endpoint.getMethod().equals(HttpMethod.PUT))
            endpoint.setData(null);

        if (updatable)
            savedEndpoints.removeIf(itEndpoint -> itEndpoint.getMethod().equals(endpoint.getMethod()) &&
                itEndpoint.getPath().equals(endpoint.getPath()));

        if (savedEndpoints.isEmpty())
            return true;

        for (Endpoint savedEndpoint : savedEndpoints) {
            if (savedEndpoint.getMethod().equals(endpoint.getMethod()) &&
                savedEndpoint.getPath().equals(endpoint.getPath())) {

                LOGGER.error("Skipping endpoint: [{} {} found in file: [{}]]. " +
                        "Reason: This endpoint already exists in file [{}]",
                    endpoint.getMethod(), endpoint.getPath(), fileName, savedEndpoint.getFileName());

                return false;
            }
        }

        return true;
    }

    /**
     *  Return the body json in one String Object.
     */
    public String getEntityBody(ContainerRequestContext requestContext) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = requestContext.getEntityStream();

        String result = null;
        try {
            ReaderWriter.writeTo(in, out);

            byte[] requestEntity = out.toByteArray();
            if (requestEntity.length == 0) {
                result = "";
            } else {
                result = new String(requestEntity, "UTF-8");
            }
            requestContext.setEntityStream(new ByteArrayInputStream(requestEntity));

        } catch (IOException e) {
            LOGGER.error("Error to read the body of this request.");

            if (lyreProperties.isDebug()) {
                e.printStackTrace();
            } else
                LOGGER.warn("\u21B3 " + "Enable debug mode to see stacktrace log");
        }
        return result;
    }
}
