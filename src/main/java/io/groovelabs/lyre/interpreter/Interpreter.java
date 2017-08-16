package io.groovelabs.lyre.interpreter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.groovelabs.lyre.domain.Bundle;
import io.groovelabs.lyre.domain.Endpoint;
import io.groovelabs.lyre.reader.Reader;

import java.util.List;
import java.util.Map;


public class Interpreter {

    private Reader reader;

    public Interpreter() {
        reader = new Reader();
    }

    public Bundle interpret() throws Exception {

        Bundle bundle = new Bundle();

        List<ObjectNode> nodes = reader.read();

        for (ObjectNode parentNode : nodes) {
            parentNode.fields().forEachRemaining(entry -> {

                Endpoint endpoint = new Endpoint();

                try {
                    this.parse(endpoint, entry, Level.ENDPOINT);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                bundle.add(endpoint);

            });
        }

        return bundle;
    }

    public void parse(Endpoint endpoint, Map.Entry<String, JsonNode> entry, Level level) {

        switch (level) {
            case ENDPOINT:

                String[] words = entry.getKey().split(" ", 4);

                if (explicit(entry.getKey())) {
                    endpoint.setPath(words[0]);
                } else {
                    endpoint.setMethod(words[0]);
                    endpoint.setPath(words[1]);
                }

                entry.getValue().fields().forEachRemaining(node ->
                    this.parse(endpoint, node, Level.PROPERTY));

                break;

            case RESPONSE:

                // TODO refact it to set status and data inside Response object (data can not be replaced).

                if (Property.STATUS.is(entry.getKey())) {

                    endpoint.setStatus(entry.getValue().asText());

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
