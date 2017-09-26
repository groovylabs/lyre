package com.github.groovylabs.lyre.test;

import com.github.groovylabs.lyre.config.LyreProperties;
import com.github.groovylabs.lyre.engine.reader.Reader;
import com.github.groovylabs.lyre.engine.scanner.Scanner;
import com.github.groovylabs.lyre.test.utils.TempIO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Import({
    LyreTestConfiguration.class
})
@TestPropertySource("classpath:application.properties")
public class WatcherTest {

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
     * Test Watcher thread creation and live-reload feature
     */
    @Test
    public void watcherTest() {

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

        //changing live-reload property to true.
        lyreProperties.setEnableLivereload(true);

        scanner.setLyreProperties(lyreProperties);

        //creating files to test - on root
        HashMap<String, String> rootEntries = new HashMap<>();
        rootEntries.put("test-watch-intact", ".lyre");
        rootEntries.put("test-watch-modified", ".lyre");

        HashMap<String, File> rootFiles = TempIO.buildFiles(directories[0], rootEntries);

        //call scanner method
        scanner.startWatcher(new ArrayList<>(rootFiles.values()));

        try {
            // Given time to watcher thread startup (TODO it can be implement event driven flow).
            Thread.sleep(250);
            TempIO.writeOnTempFile(rootFiles.get("test-watch-modified.lyre"), "watching");
        } catch (IOException | InterruptedException e) {
            fail("Couldn't write on temporary file: " + e.getMessage());
        }

        //should have a watcher thread instance
        assertThat(scanner.getWatcherInstance()).isNotNull();
        assertThat(scanner.getWatcher()).isNotNull();
        verify(reader, times(1)).read(any());
        assertThat(scanner.getWatcher().getFiles()).extracting("name")
            .contains(
                rootFiles.get("test-watch-modified.lyre").getName());

    }

}
