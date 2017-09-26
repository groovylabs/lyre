package com.github.groovylabs.lyre.test.utils;

import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class TempIO {

    private static File[] tempDirectories = null;

    private static final Logger LOGGER = LoggerFactory.getLogger(TempIO.class);

    public static File[] createTempDirectories() throws IOException {

        if (tempDirectories != null)
            return tempDirectories;
        else {
            // creating temp directory to create temporary files on it.
            File rootLevel = Files.createTempDir();
            File firstLevel = new File(rootLevel, "first");
            File secondLevel = new File(firstLevel, "second");
            File thirdLevel = new File(secondLevel, "third");

            //levels will be deleted after exit
            rootLevel.deleteOnExit();
            firstLevel.deleteOnExit();
            secondLevel.deleteOnExit();
            thirdLevel.deleteOnExit();

            boolean directories = thirdLevel.mkdirs();

            if (directories) {
                tempDirectories = new File[]{rootLevel, firstLevel, secondLevel, thirdLevel};
                return tempDirectories;
            } else
                throw new IOException();
        }

    }

    public static HashMap<String, File> buildFiles(File directory, HashMap<String, String> entries) {

        HashMap<String, File> files = new HashMap<>();

        entries.entrySet().forEach(entry -> {
            try {
                File file = File.createTempFile(entry.getKey(), entry.getValue(), directory);
                file.deleteOnExit();
                files.put(entry.getKey() + entry.getValue(), file);
            } catch (IOException e) {
                LOGGER.error("Unable to create temporary file: {} on path: {}", entry.getKey() + entry.getValue(), directory.getAbsolutePath());
                e.printStackTrace();
            }
        });

        return files;
    }

    public static void writeOnTempFile(File file, String message) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(message);

        writer.close();

//        Path path = Paths.get(file.getAbsolutePath());
//        byte[] strToBytes = message.getBytes();
//
//        java.nio.file.Files.write(path, strToBytes);

    }
}
