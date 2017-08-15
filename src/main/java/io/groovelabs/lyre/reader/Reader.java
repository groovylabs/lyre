package io.groovelabs.lyre.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.groovelabs.lyre.scanner.Scanner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Reader {

    private Scanner scanner;

    public Reader() {
        scanner = new Scanner();
    }

    public List<ObjectNode> read() {

        List<ObjectNode> objectNodes = new ArrayList<>();

//        ObjectNode objectNode = null;

//        ClassLoader classLoader = getClass().getClassLoader();

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        try {

            List<File> lyreFiles = scanner.scan();

            for (File lyreFile : lyreFiles)
                objectNodes.add(mapper.readValue(lyreFile, ObjectNode.class));

            System.out.println(objectNodes);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return objectNodes;
    }
}
