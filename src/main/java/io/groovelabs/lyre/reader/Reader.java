package io.groovelabs.lyre.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.groovelabs.lyre.APIx.engine.Overlay;
import io.groovelabs.lyre.interpreter.Interpreter;
import io.groovelabs.lyre.scanner.Scanner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Reader extends Overlay<Interpreter> {

    private Scanner scanner;

    public Reader(Interpreter interpreter) {
        super(interpreter);

        scanner = new Scanner(this);
    }

    public void read(List<File> lyreFiles) {

        List<ObjectNode> objectNodes = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        try {

            for (File lyreFile : lyreFiles)
                objectNodes.add(mapper.readValue(lyreFile, ObjectNode.class));

            System.out.println(objectNodes);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        overlay().interpret(objectNodes);
    }
}
