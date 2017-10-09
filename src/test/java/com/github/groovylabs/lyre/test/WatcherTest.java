package com.github.groovylabs.lyre.test;

import com.github.groovylabs.lyre.engine.scanner.Scanner;
import com.github.groovylabs.lyre.test.configurations.ScannerConfiguration;
import com.github.groovylabs.lyre.test.initializations.InitializingResourceBean;
import com.github.groovylabs.lyre.test.tools.Resources;
import com.github.groovylabs.lyre.test.tools.TempIO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Import({ScannerConfiguration.class})
public class WatcherTest extends InitializingResourceBean {

    @Autowired
    private Resources resources;

    @Autowired
    private Scanner scanner;

    @Before
    public void init() {
        //changing live-reload property to true.
        scanner.getLyreProperties().setEnableLivereload(true);
    }

    /**
     * Test Watcher thread creation and live-reload feature
     */
    @Test
    public void watcherTest() {

        //creating files to test - on root
        HashMap<String, String> rootEntries = new HashMap<>();
        rootEntries.put("test-watch-intact", ".lyre");
        rootEntries.put("test-watch-modified", ".lyre");

        HashMap<String, File> rootFiles = TempIO.buildFiles(resources.getDirectory(0), rootEntries);

        //call scanner method
        scanner.startWatcher(new ArrayList<>(rootFiles.values()));

        try {
            TempIO.writeOnTempFile(rootFiles.get("test-watch-modified.lyre"), "watching");
        } catch (IOException e) {
            fail("Couldn't write on temporary file: " + e.getMessage());
        }

        //should have a watcher thread instance
        assertThat(scanner.getWatcherInstance()).isNotNull();
        assertThat(scanner.getWatcher()).isNotNull();
        assertThat(scanner.getWatcher().getFiles()).extracting("name")
            .contains(
                rootFiles.get("test-watch-modified.lyre").getName());

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
