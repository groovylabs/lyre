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

package com.github.groovylabs.lyre.engine.scanner;

import com.github.groovylabs.lyre.config.LyreProperties;
import com.github.groovylabs.lyre.engine.Overlay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class Watcher extends Overlay<Scanner> implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Watcher.class);

    private LyreProperties lyreProperties;

    private final Path path;

    private List<File> files;

    public Watcher(Scanner scanner, List<File> files, LyreProperties lyreProperties) {
        super(scanner);

        LOGGER.info("Watching [*{}] files on path: [{}]",
            lyreProperties.getFileFormat(), lyreProperties.getScanPath());

        this.path = FileSystems.getDefault().getPath(lyreProperties.getScanPath());
        this.files = files;
        this.lyreProperties = lyreProperties;
    }

    @Override
    public void run() {
        try (final WatchService watchService = FileSystems.getDefault().newWatchService()) {

            path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

            while (true) {

                final WatchKey wk = watchService.take();

                for (WatchEvent<?> event : wk.pollEvents()) {

                    final Path changedPath = (Path) event.context();
                    File changedFile = new File(changedPath.getFileName().toString());

                    if (changedFile.getName().endsWith(lyreProperties.getFileFormat())) {
                        for (File file : files) {
                            if (file.getName().equals(changedFile.getName())) {

                                LOGGER.info("Loading new file [{}] on path [{}]", file.getName(), changedPath);
                                overlay().getReader().read(file);
                                break;
                            }
                        }
                    }
                }

                wk.reset();
            }
        } catch (IOException | InterruptedException e) {

            LOGGER.error("Error during watch files");

            if (lyreProperties.isDebug()) {
                e.printStackTrace();
            } else
                LOGGER.warn("\u21B3 " + "Enable debug mode to see stacktrace log");

        }
    }

    public final List<File> getFiles() {
        return files;
    }
}
