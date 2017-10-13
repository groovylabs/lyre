/*
 * MIT License
 *
 * Copyright (c) 2017 Groovylabs
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

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
