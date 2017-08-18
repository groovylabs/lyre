package io.groovelabs.lyre.validator;

import io.groovelabs.lyre.domain.Endpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(Validator.class);

    /**
     * Responsible to check the integrity of endpoint before insert it into list and verify if it's already been inserted.
     */
    public static boolean integrity(String fileName, Endpoint endpoint, List<Endpoint> savedEndpoints) {

        if (savedEndpoints.isEmpty())
            return true;

        for (Endpoint savedEndpoint : savedEndpoints) {
            if (savedEndpoint.getMethod().equals(endpoint.getMethod()) && savedEndpoint.getPath().equals(endpoint.getPath())) {
                LOGGER.error("Fail to insert the follow endpoint: [{} {} -> FILE [{}]]. Reason: Because this endpoint already exists in the file [{}]",
                    endpoint.getMethod(), endpoint.getPath(), fileName, savedEndpoint.getFileName());
                return false;
            }
        }

        return true;
    }
}
