package io.groovelabs.lyre.reader;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.groovelabs.lyre.domain.Endpoint;
import io.groovelabs.lyre.domain.LyreFile;
import io.groovelabs.lyre.domain.enums.FileType;
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

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        ObjectMapper mapperJson = new ObjectMapper(new JsonFactory());

        try {

            List<LyreFile> lyreFiles = scanner.scan();

            for (LyreFile lyreFile : lyreFiles) {
                if (lyreFile.getFileType().equals(FileType.YAML)) {
                    objectNodes.add(mapper.readValue(lyreFile.getFile(), ObjectNode.class));
                } else {
                    objectNodes.add(mapperJson.readValue(lyreFile.getFile(), ObjectNode.class));
                }
            }

            System.out.println(objectNodes);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return objectNodes;
    }
}
