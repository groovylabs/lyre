package com.github.groovylabs.lyre.test;

import com.github.groovylabs.lyre.config.LyreProperties;
import com.github.groovylabs.lyre.engine.scanner.Scanner;
import com.github.groovylabs.lyre.test.configurations.LyrePropertiesConfiguration;
import com.github.groovylabs.lyre.test.configurations.ResourcesConfiguration;
import com.github.groovylabs.lyre.test.configurations.ScannerConfiguration;
import com.github.groovylabs.lyre.test.initializations.InitializingResourceBean;
import com.github.groovylabs.lyre.test.tools.Resources;
import com.github.groovylabs.lyre.test.tools.TempIO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Import({
    LyrePropertiesConfiguration.class,
    ResourcesConfiguration.class,
    ScannerConfiguration.class
})
@TestPropertySource("classpath:application.properties")
public class ScannerTest extends InitializingResourceBean {

    @Autowired
    private Resources resources;

    @Autowired
    private Scanner scanner;

    /**
     * Test Scanner method: scan()
     * Scanner must be able to find all files in a specific path and with a specific extension {@link LyreProperties}
     */
    @Test
    public void scannerTest() {

        // temporary hash of files to be tested
        HashMap<String, File> tempFiles = new HashMap<>();

        //creating files to test - on root
        HashMap<String, String> rootEntries = new HashMap<>();
        rootEntries.put("test-root-success", ".lyre");
        rootEntries.put("test-root-wrong-extension", ".ext");

        HashMap<String, File> rootFiles = TempIO.buildFiles(resources.getDirectory(0), rootEntries);

        //creating files to test - on first level
        HashMap<String, String> firstEntries = new HashMap<>();
        firstEntries.put("test-first-success", ".lyre");
        firstEntries.put("test-first-wrong-extension", ".ext");

        HashMap<String, File> firstFiles = TempIO.buildFiles(resources.getDirectory(1), firstEntries);

        //creating files to test - on second level
        HashMap<String, String> secondEntries = new HashMap<>();
        secondEntries.put("test-second-success", ".lyre");
        secondEntries.put("test-second-wrong-extension", ".ext");

        HashMap<String, File> secondFiles = TempIO.buildFiles(resources.getDirectory(2), secondEntries);

        //creating files to test - on third level
        HashMap<String, String> thirdEntries = new HashMap<>();
        thirdEntries.put("test-third-success", ".lyre");
        thirdEntries.put("test-third-wrong-extension", ".ext");

        HashMap<String, File> thirdFiles = TempIO.buildFiles(resources.getDirectory(3), thirdEntries);

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

    @Override
    public Resources getResources() {
        return resources;
    }

    @Override
    public Scanner getScanner() {
        return scanner;
    }
}
