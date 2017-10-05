
package com.github.groovylabs.lyre.engine.interpreter;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.groovylabs.lyre.config.LyreProperties;
import com.github.groovylabs.lyre.domain.Bundle;
import com.github.groovylabs.lyre.domain.Endpoint;
import com.github.groovylabs.lyre.domain.enums.Level;
import com.github.groovylabs.lyre.engine.APIx.controller.APIxController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Interpreter extends Parser {

    @Autowired
    private LyreProperties lyreProperties;

    @Autowired
    private APIxController apixController;

    @Autowired
    private Bundle bundle;

    private boolean update = false;

    public void interpret(Map<String, ObjectNode> nodes) {

        for (Map.Entry<String, ObjectNode> object : nodes.entrySet()) {

            fileName = object.getKey();
            ObjectNode parentNode = object.getValue();

            parentNode.fields().forEachRemaining(entry -> {

                Endpoint endpoint = new Endpoint();

                try {

                    this.parse(endpoint, entry, Level.ENDPOINT);

                    if (validator.integrity(fileName, endpoint, bundle.getEndpoints(), update)) {
                        endpoint.createHash();
                        bundle.add(endpoint);
                    }

                } catch (Exception e) {

                    LOGGER.error("Error parsing file [{}] to endpoint bundle", fileName);

                    if (lyreProperties.isDebug()) {
                        e.printStackTrace();
                    } else
                        LOGGER.warn("\u21B3 " + "Enable debug mode to see stacktrace log");

                }

            });

        }

        update = true;

        apixController.bootAttempt(nodes.entrySet().size() + " file resource(s)");
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}
