package groovylabs.lyre.engine.interpreter;

import com.fasterxml.jackson.databind.JsonNode;
import groovylabs.lyre.domain.Endpoint;
import groovylabs.lyre.domain.appliers.Countdown;
import groovylabs.lyre.domain.enums.Level;
import groovylabs.lyre.domain.enums.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.Map;

public abstract class Parser {

    private static final Logger LOGGER = LoggerFactory.getLogger(Parser.class);

    protected String fileName;

    protected void parse(Endpoint endpoint, Map.Entry<String, JsonNode> entry, Level level) {

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

                } else if (Property.DATA.is(entry.getKey())) {

                    endpoint.setData(entry.getValue().asText());

                } else if (Property.RESPONSE.is(entry.getKey())) {

                    entry.getValue().fields().forEachRemaining(node ->
                        this.parse(endpoint, node, Level.RESPONSE));

                } else if (Property.SETUP.is(entry.getKey())) {

                    entry.getValue().fields().forEachRemaining(node ->
                        this.parse(endpoint, node, Level.SETUP));

                } else {
                    unrecognizedElement(entry.getKey(), level);
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
                    unrecognizedElement(entry.getKey(), level);
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
                    unrecognizedElement(entry.getKey(), level);
                }

            case PARAMETER:
                break;

        }

    }

    private void unrecognizedElement(String key, Level level) {
        LOGGER.warn("Unrecognized element [{}] on [{}] level, inside file: [{}]", key, level, fileName);
    }

    private boolean explicit(String entry) {
        return entry.startsWith("/");
    }

}
