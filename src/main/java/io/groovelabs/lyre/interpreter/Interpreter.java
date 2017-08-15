package io.groovelabs.lyre.interpreter;

import com.fasterxml.jackson.databind.JsonNode;
import io.groovelabs.lyre.domain.Bundle;
import io.groovelabs.lyre.domain.Endpoint;
import io.groovelabs.lyre.reader.Reader;

public class Interpreter {

    private Reader reader;

    public Interpreter() {
        reader = new Reader();
    }

    public Bundle interpret() throws Exception {

        Bundle bundle = new Bundle();

        reader.read().fields().forEachRemaining(entry -> {

            Endpoint e = new Endpoint();

            // explicit mode
            if (explicit(entry.getKey())) {

                System.out.println("explicit");

            } else {

                try {

                    System.out.println("implicit");

                    String[] words = entry.getKey().split(" ", 4);

                    e.setMethod(words[0]);
                    e.setPath(words[1]);

                    JsonNode children = entry.getValue();

                    children.fields().forEachRemaining(child -> {

                        if (child.getKey().equals("response")) {

                            JsonNode node = child.getValue();

                            node.fields().forEachRemaining(responses -> {

                                try {
                                    if (responses.getKey().equals("status"))
                                        e.setStatus(responses.getValue().asText());
                                    else if (responses.getKey().equals("data")) {
                                        e.setData(responses.getValue().asText());
                                    }
                                } catch (Exception ex2) {
                                    System.out.println("parse error");
                                }

                            });
                        }


                    });


                } catch (Exception ex) {
                    System.out.println("parse error");
                }

            }

            bundle.add(e);

        });

        return bundle;
    }

    private boolean explicit(String entry) {
        return entry.startsWith("/");
    }

}
