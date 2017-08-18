package io.groovelabs.lyre.engine.reader;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.groovelabs.lyre.engine.Overlay;
import io.groovelabs.lyre.engine.interpreter.Interpreter;
import io.groovelabs.lyre.engine.scanner.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Reader extends Overlay<Interpreter> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Reader.class);

    public Reader(Interpreter interpreter) {
        super(interpreter);

        new Scanner(this);
    }

    public void read(File... files) {

        Map<String, ObjectNode> objectNodes = new HashMap<>();

        for (File file : files)
            readFile(file, objectNodes);

        overlay().interpret(objectNodes);
    }

    private void readFile(File lyreFile, Map<String, ObjectNode> objectNodes) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        ObjectMapper mapperJson = new ObjectMapper(new JsonFactory());

        try {
            objectNodes.put(lyreFile.getPath(), mapper.readValue(lyreFile, ObjectNode.class));
        } catch (JsonParseException | JsonMappingException fileMapperException) {
            try {
                objectNodes.put(lyreFile.getPath(), mapperJson.readValue(lyreFile, ObjectNode.class));
            } catch (JsonParseException | JsonMappingException fileMapperException2) {
                LOGGER.error("Error to mount JSON or YAML from file [{}]", lyreFile.getName());
            } catch (IOException io2) {
                LOGGER.error("Error to read the file [{}]", lyreFile.getName());
            }
        } catch (IOException io) {
            LOGGER.error("Error to read the file [{}]", lyreFile.getName());
        }
    }
}
