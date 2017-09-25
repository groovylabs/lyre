package com.github.groovylabs.lyre.test;

import com.github.groovylabs.lyre.config.LyreProperties;
import com.github.groovylabs.lyre.engine.reader.Reader;
import com.github.groovylabs.lyre.engine.scanner.Scanner;
import com.google.common.io.Files;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Import({
    LyreTestConfiguration.class
})
@TestPropertySource("classpath:application.properties")
public class ScannerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScannerTest.class);

    @MockBean
    private Reader reader;

    @Autowired
    private LyreProperties lyreProperties;

    private Scanner scanner;

    @Before
    public void init() {
        scanner = new Scanner();
        scanner.setReader(reader);
    }

    /**
     * Test Scanner method: scan()
     * Scanner must be able to find all files in a specific path and with a specific extension {@link LyreProperties}
     */
    @Test
    public void scannerTest() {

        // creating temp directory to create temporary files on it.
        File rootLevel = createTempDiretory();
        File firstLevel = new File(rootLevel, "first");
        File secondLevel = new File(firstLevel, "second");
        File thirdLevel = new File(secondLevel, "third");

        boolean directories = thirdLevel.mkdirs();

        //changing default test path to temp directory
        lyreProperties.setScanPath(rootLevel.getAbsolutePath());
        scanner.setLyreProperties(lyreProperties);

        // temporary hash of files to be tested
        HashMap<String, File> tempFiles = new HashMap<>();

        //creating files to test - on root
        HashMap<String, String> rootEntries = new HashMap<>();
        rootEntries.put("test-root-success", ".lyre");
        rootEntries.put("test-root-wrong-extension", ".ext");

        HashMap<String, File> rootFiles = buildFiles(rootLevel, rootEntries);

        //creating files to test - on first level
        HashMap<String, String> firstEntries = new HashMap<>();
        firstEntries.put("test-first-success", ".lyre");
        firstEntries.put("test-first-wrong-extension", ".ext");

        HashMap<String, File> firstFiles = buildFiles(firstLevel, firstEntries);

        //creating files to test - on second level
        HashMap<String, String> secondEntries = new HashMap<>();
        secondEntries.put("test-second-success", ".lyre");
        secondEntries.put("test-second-wrong-extension", ".ext");

        HashMap<String, File> secondFiles = buildFiles(secondLevel, secondEntries);

        //creating files to test - on third level
        HashMap<String, String> thirdEntries = new HashMap<>();
        thirdEntries.put("test-third-success", ".lyre");
        thirdEntries.put("test-third-wrong-extension", ".ext");

        HashMap<String, File> thirdFiles = buildFiles(thirdLevel, thirdEntries);

        tempFiles.putAll(rootFiles);
        tempFiles.putAll(firstFiles);
        tempFiles.putAll(secondFiles);
        tempFiles.putAll(thirdFiles);

        //call scanner method
        scanner.scan();

        assertThat(directories).isTrue();

        assertThat(scanner.getFiles()).isNotNull();

        //should scan files:
        assertThat(scanner.getFiles()).extracting("name")
            .contains(
                tempFiles.get("test-root-success.lyre").getName(),
                tempFiles.get("test-first-success.lyre").getName(),
                tempFiles.get("test-second-success.lyre").getName(),
                tempFiles.get("test-third-success.lyre").getName());

        //should not scan files:
        assertThat(scanner.getFiles()).extracting("name")
            .doesNotContain(
                tempFiles.get("test-root-wrong-extension.ext").getName(),
                tempFiles.get("test-first-wrong-extension.ext").getName(),
                tempFiles.get("test-second-wrong-extension.ext").getName(),
                tempFiles.get("test-third-wrong-extension.ext").getName());
    }

    private File createTempDiretory() {

        File tempDir = Files.createTempDir();
        tempDir.deleteOnExit();

        return tempDir;
    }

    private HashMap<String, File> buildFiles(File directory, HashMap<String, String> entries) {

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

}
