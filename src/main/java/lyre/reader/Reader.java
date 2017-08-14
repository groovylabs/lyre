package lyre.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.sun.org.apache.regexp.internal.RE;
import lyre.scanner.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

public class Reader {

    private Scanner scanner;

    public Reader() {
        scanner = new Scanner();
    }

    public ObjectNode read() {

        ObjectNode objectNode = null;

        ClassLoader classLoader = getClass().getClassLoader();

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        try {

            // TODO Scan all *.lyre files on project on Scanner Class
            objectNode = mapper.readValue(new File(classLoader.getResource("endpoint.lyre").getFile()), ObjectNode.class);

            System.out.println(objectNode);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return objectNode;
    }
}
