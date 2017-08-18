package io.groovelabs.lyre.interpreter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.groovelabs.lyre.domain.Bundle;
import io.groovelabs.lyre.domain.Endpoint;
import io.groovelabs.lyre.reader.Reader;
import io.groovelabs.lyre.validator.Validator;

import java.util.Map;


public class Interpreter {

    private Reader reader;

    public Interpreter() {
        reader = new Reader();
    }

    public Bundle interpret() throws Exception {

        Bundle bundle = new Bundle();

        Map<String, ObjectNode> nodes = reader.read();

        for (Map.Entry<String, ObjectNode> object : nodes.entrySet()) {

            String fileName = object.getKey();
            ObjectNode parentNode = object.getValue();

            parentNode.fields().forEachRemaining(entry -> {

                Endpoint endpoint = new Endpoint();

                try {
                    this.parse(endpoint, entry, Level.ENDPOINT, fileName);

                    if (Validator.integrity(fileName, endpoint, bundle.getList()))
                        bundle.add(endpoint);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            });

        }

//        for (ObjectNode parentNode : nodes) {

//        }

        return bundle;
    }

    public void parse(Endpoint endpoint, Map.Entry<String, JsonNode> entry, Level level, String fileName) {

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
                    this.parse(endpoint, node, Level.PROPERTY, fileName));

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
                        this.parse(endpoint, node, Level.PARAMETER, fileName));

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
                        this.parse(endpoint, node, Level.RESPONSE, fileName));

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
