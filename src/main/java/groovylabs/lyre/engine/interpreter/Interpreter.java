
package groovylabs.lyre.engine.interpreter;

import com.fasterxml.jackson.databind.node.ObjectNode;
import groovylabs.lyre.config.LyreProperties;
import groovylabs.lyre.domain.Bundle;
import groovylabs.lyre.domain.Endpoint;
import groovylabs.lyre.domain.enums.Level;
import groovylabs.lyre.domain.factories.BundleFactory;
import groovylabs.lyre.engine.APIx.APIx;
import groovylabs.lyre.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class Interpreter extends Parser {

    private static final Logger LOGGER = LoggerFactory.getLogger(Interpreter.class);

    @Autowired
    private LyreProperties lyreProperties;

    @Autowired
    private APIx apix;

    @Autowired
    private Validator validator;

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

        apix.boot();
    }

}
