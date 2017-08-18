
package io.groovelabs.lyre.engine.interpreter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.groovelabs.lyre.domain.Bundle;
import io.groovelabs.lyre.domain.Endpoint;
import io.groovelabs.lyre.engine.APIx.APIx;
import io.groovelabs.lyre.engine.Overlay;
import io.groovelabs.lyre.engine.reader.Reader;
import io.groovelabs.lyre.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Interpreter extends Overlay<APIx> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Interpreter.class);

    private String fileName;

    private boolean update = false;

    private Bundle bundle = new Bundle();

    public Interpreter(APIx apix) {
        super(apix);

        new Reader(this);
    }

    public void interpret(Map<String, ObjectNode> nodes) {

        for (Map.Entry<String, ObjectNode> object : nodes.entrySet()) {

            fileName = object.getKey();
            ObjectNode parentNode = object.getValue();

            parentNode.fields().forEachRemaining(entry -> {

                Endpoint endpoint = new Endpoint();

                try {
                    this.parse(endpoint, entry, Level.ENDPOINT);

                    if (Validator.integrity(fileName, endpoint, bundle.getList(), update))
                        bundle.add(endpoint);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            });

        }

        update = true;

        overlay().boot(bundle);
    }

    private void parse(Endpoint endpoint, Map.Entry<String, JsonNode> entry, Level level) {

        switch (level) {
            case ENDPOINT:

                String[] words = entry.getKey().split(" ", 4);

                if (explicit(entry.getKey())) {
                    endpoint.setPath(words[0]);
                } else {
                    endpoint.setMethod(words[0]);
                    endpoint.setPath(words[1]);
                }

                endpoint.setFileName(fileName);

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

                } else if (Property.RESPONSE.is(entry.getKey())) {

                    entry.getValue().fields().forEachRemaining(node ->
                        this.parse(endpoint, node, Level.RESPONSE));

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

                } else if (Property.COOKIE.is(entry.getKey())) {

                    // TODO implement cookie parser

                } else {
                    LOGGER.warn("Unrecognized element [{}] on [{}] level, inside file: [{}]", entry.getKey(), level, fileName);
                }

                break;

            case PARAMETER:
                break;

        }

    }

    private boolean explicit(String entry) {
        return entry.startsWith("/");
    }

}
