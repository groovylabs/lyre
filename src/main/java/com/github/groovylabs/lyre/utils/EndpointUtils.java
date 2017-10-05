package com.github.groovylabs.lyre.utils;

import com.github.groovylabs.lyre.config.LyreProperties;
import org.glassfish.jersey.message.internal.ReaderWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.container.ContainerRequestContext;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class EndpointUtils {

    @Autowired
    private LyreProperties lyreProperties;

    private static final Logger LOGGER = LoggerFactory.getLogger(EndpointUtils.class);

    /**
     * Return the body json in one String Object.
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
