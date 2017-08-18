
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

    public Interpreter(APIx apix) {
        super(apix);

        new Reader(this);
    }

    public void interpret(Map<String, ObjectNode> nodes) {

        Bundle bundle = new Bundle();

        for (Map.Entry<String, ObjectNode> object : nodes.entrySet()) {

            fileName = object.getKey();
            ObjectNode parentNode = object.getValue();

            parentNode.fields().forEachRemaining(entry -> {

                Endpoint endpoint = new Endpoint();

                try {
                    this.parse(endpoint, entry, Level.ENDPOINT);

                    if (Validator.integrity(fileName, endpoint, bundle.getList()))
                        bundle.add(endpoint);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            });

        }

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
                    this.parse(endpoint, node, Level.PROPERTY));

                break;

            case RESPONSE:

                // TODO refact it to set status and data inside Response object (data can not be replaced).

                if (Property.STATUS.is(entry.getKey())) {

                    endpoint.getResponse().setStatus(entry.getValue().asText());

                } else if (Property.DATA.is(entry.getKey())) {

                    endpoint.setData(entry.getValue().asText());

                }

            case PROPERTY:

                if (Property.PARAMS.is(entry.getKey())) {

                    entry.getValue().fields().forEachRemaining(node ->
                        this.parse(endpoint, node, Level.PARAMETER));

                } else if (Property.VALUE.is(entry.getKey())) {

                    endpoint.setPath(endpoint.getPath() + entry.getValue().asText());

                } else if (Property.METHOD.is(entry.getKey())) {

                    endpoint.setMethod(entry.getValue().asText());

                } else if (Property.DATA.is(entry.getKey())) {

                    endpoint.setData(entry.getValue().asText());

                } else if (Property.COOKIE.is(entry.getKey())) {

                    // TODO implement cookie parser

                } else if (Property.RESPONSE.is(entry.getKey())) {

                    entry.getValue().fields().forEachRemaining(node ->
                        this.parse(endpoint, node, Level.RESPONSE));

                } else {

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
