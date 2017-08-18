package io.groovelabs.lyre.reader;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.groovelabs.lyre.scanner.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reader {

    private static final Logger LOGGER = LoggerFactory.getLogger(Reader.class);

    private Map<String, ObjectNode> objectNodes = new HashMap<>();

    private Scanner scanner;

    public Reader() {
        scanner = new Scanner();
    }

    public Map<String, ObjectNode> read() {

        List<File> lyreFiles = scanner.scan();

        for (File lyreFile : lyreFiles)
            readFile(lyreFile);

        return objectNodes;
    }

    private void readFile(File lyreFile) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        ObjectMapper mapperJson = new ObjectMapper(new JsonFactory());

        try {
            objectNodes.put(lyreFile.getName(), mapper.readValue(lyreFile, ObjectNode.class));
        } catch (JsonParseException | JsonMappingException fileMapperException) {
            try {
                objectNodes.put(lyreFile.getName(), mapperJson.readValue(lyreFile, ObjectNode.class));
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
