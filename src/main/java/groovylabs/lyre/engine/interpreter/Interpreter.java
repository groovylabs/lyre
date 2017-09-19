
package groovylabs.lyre.engine.interpreter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import groovylabs.lyre.domain.Bundle;
import groovylabs.lyre.domain.Endpoint;
import groovylabs.lyre.domain.appliers.Countdown;
import groovylabs.lyre.engine.APIx.APIx;
import groovylabs.lyre.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Interpreter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Interpreter.class);

    @Autowired
    private APIx apix;

    private String fileName;

    private boolean update = false;

    private Bundle bundle = new Bundle();

    public void interpret(Map<String, ObjectNode> nodes) {

        for (Map.Entry<String, ObjectNode> object : nodes.entrySet()) {

            fileName = object.getKey();
            ObjectNode parentNode = object.getValue();

            parentNode.fields().forEachRemaining(entry -> {

                Endpoint endpoint = new Endpoint();

                try {
                    this.parse(endpoint, entry, Level.ENDPOINT);

                    if (Validator.integrity(fileName, endpoint, bundle.getEndpoints(), update)) {
                        endpoint.createHash();
                        bundle.add(endpoint);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            });

        }

        update = true;

        apix.boot(bundle);
    }

    private void parse(Endpoint endpoint, Map.Entry<String, JsonNode> entry, Level level) {

        switch (level) {
            case ENDPOINT:

                endpoint.setFileName(fileName);

                String[] words = entry.getKey().split(" ", 4);

                if (explicit(entry.getKey())) {
                    endpoint.setPath(words[0]);
                } else {
                    endpoint.setMethod(words[0]);
                    endpoint.setPath(words[1]);
                }

                entry.getValue().fields().forEachRemaining(node ->
                    this.parse(endpoint, node, Level.REQUEST));

                break;
            case REQUEST:

                if (Property.VALUE.is(entry.getKey())) {

                    endpoint.setPath(endpoint.getPath() + entry.getValue().asText());

                } else if (Property.METHOD.is(entry.getKey())) {

                    endpoint.setMethod(entry.getValue().asText());

                } else if (Property.CONSUMES.is(entry.getKey())) {

                    endpoint.setConsumes(entry.getValue().asText());

                } else if (Property.IDLE.is(entry.getKey())) {

                    endpoint.getTimer().setIdle(entry.getValue().asLong(-1));

                } else if (Property.RESPONSE.is(entry.getKey())) {

                    entry.getValue().fields().forEachRemaining(node ->
                        this.parse(endpoint, node, Level.RESPONSE));

                } else if (Property.SETUP.is(entry.getKey())) {

                    entry.getValue().fields().forEachRemaining(node ->
                        this.parse(endpoint, node, Level.SETUP));

                } else {
                    LOGGER.warn("Unrecognized element: [{}] on [{}] level, inside file: [{}]", entry.getKey(), level, fileName);
                }

                break;
            case RESPONSE:

                if (Property.STATUS.is(entry.getKey())) {

                    endpoint.getResponse().setStatus(entry.getValue().asText());

                } else if (Property.PRODUCES.is(entry.getKey())) {

                    endpoint.getResponse().setProduces(entry.getValue().asText());

                } else if (Property.DATA.is(entry.getKey())) {

                    endpoint.getResponse().setData(entry.getValue().asText());

                } else {
                    LOGGER.warn("Unrecognized element [{}] on [{}] level, inside file: [{}]", entry.getKey(), level, fileName);
                }

                break;
            case SETUP:

                if (Property.BUSY.is(entry.getKey())) {

                    // TODO factory to make Countdown
                    endpoint.getSetup().setCountdown(new Countdown(HttpStatus.TOO_MANY_REQUESTS, entry.getValue().asLong(-1)));

                } else if (Property.BROKEN.is(entry.getKey())) {

                    // TODO factory to make Countdown
                    endpoint.getSetup().setCountdown(new Countdown(HttpStatus.INTERNAL_SERVER_ERROR, entry.getValue().asLong(-1)));

                } else if (Property.FORBIDDEN.is(entry.getKey())) {

                    // TODO factory to make Countdown
                    endpoint.getSetup().setCountdown(new Countdown(HttpStatus.FORBIDDEN, entry.getValue().asLong(-1)));

                } else {
                    LOGGER.warn("Unrecognized element [{}] on [{}] level, inside file: [{}]", entry.getKey(), level, fileName);
                }

            case PARAMETER:
                break;

        }

    }

    private boolean explicit(String entry) {
        return entry.startsWith("/");
    }

}
