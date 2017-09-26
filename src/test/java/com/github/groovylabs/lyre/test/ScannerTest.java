package com.github.groovylabs.lyre.test;

import com.github.groovylabs.lyre.config.LyreProperties;
import com.github.groovylabs.lyre.engine.reader.Reader;
import com.github.groovylabs.lyre.engine.scanner.Scanner;
import com.github.groovylabs.lyre.test.utils.TempIO;
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
import java.util.Arrays;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Import({
    LyreTestConfiguration.class
})
@TestPropertySource("classpath:application.properties")
public class ScannerTest {

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

        File[] directories = null;

        try {
            // creating temp directory to create temporary files on it.
            directories = TempIO.createTempDirectories();
        } catch (IOException e) {
            fail("Couldn't create temporary directories: " + e.getMessage());
        }

        assertThat(directories).isNotNull();
        Arrays.stream(directories).forEach(dir -> assertThat(dir).isNotNull());

        //changing default test path to temp directory
        lyreProperties.setScanPath(directories[0].getAbsolutePath());
        scanner.setLyreProperties(lyreProperties);

        // temporary hash of files to be tested
        HashMap<String, File> tempFiles = new HashMap<>();

        //creating files to test - on root
        HashMap<String, String> rootEntries = new HashMap<>();
        rootEntries.put("test-root-success", ".lyre");
        rootEntries.put("test-root-wrong-extension", ".ext");

        HashMap<String, File> rootFiles = TempIO.buildFiles(directories[0], rootEntries);

        //creating files to test - on first level
        HashMap<String, String> firstEntries = new HashMap<>();
        firstEntries.put("test-first-success", ".lyre");
        firstEntries.put("test-first-wrong-extension", ".ext");

        HashMap<String, File> firstFiles = TempIO.buildFiles(directories[1], firstEntries);

        //creating files to test - on second level
        HashMap<String, String> secondEntries = new HashMap<>();
        secondEntries.put("test-second-success", ".lyre");
        secondEntries.put("test-second-wrong-extension", ".ext");

        HashMap<String, File> secondFiles = TempIO.buildFiles(directories[2], secondEntries);

        //creating files to test - on third level
        HashMap<String, String> thirdEntries = new HashMap<>();
        thirdEntries.put("test-third-success", ".lyre");
        thirdEntries.put("test-third-wrong-extension", ".ext");

        HashMap<String, File> thirdFiles = TempIO.buildFiles(directories[3], thirdEntries);

        tempFiles.putAll(rootFiles);
        tempFiles.putAll(firstFiles);
        tempFiles.putAll(secondFiles);
        tempFiles.putAll(thirdFiles);

        //call scanner method
        scanner.scan();

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

}
