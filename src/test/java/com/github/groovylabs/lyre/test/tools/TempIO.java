package com.github.groovylabs.lyre.test.tools;

import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

        entries.entrySet().forEach(entry ->
            files.put(entry.getKey() + entry.getValue(), TempIO.buildFile(entry.getKey(), entry.getValue(), directory))
        );

        return files;
    }

    public static File buildFile(String name, String extension, File directory) {

        File file = null;

        try {
            file = File.createTempFile(name, extension, directory);
            file.deleteOnExit();
        } catch (IOException e) {
            LOGGER.error("Unable to create temporary file: {} on path: {}", name + extension, directory.getAbsolutePath());
            e.printStackTrace();
        }

        return file;
    }

    public static void writeOnTempFile(File file, String message) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(message);
        writer.close();

    }
}
