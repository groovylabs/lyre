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
import com.github.groovylabs.lyre.engine.reader.Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class Scanner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Scanner.class);

    private LyreProperties lyreProperties;

    private Reader reader;

    private List<File> files = new ArrayList<>();

    private Thread watcherInstance = null;

    private Watcher watcher = null;

    @Autowired
    public Scanner(LyreProperties lyreProperties, Reader reader) {
        this.lyreProperties = lyreProperties;
        this.reader = reader;
        scan();
    }

    public void scan() {

        LOGGER.info("Scanning [*{}] files on path: [{}]",
            lyreProperties.getFileFormat(), lyreProperties.getScanPath());

        File folder = new File(lyreProperties.getScanPath());

        if (folder.isDirectory()) {

            File[] fileList = folder.listFiles();

            searchFiles(fileList);

            startWatcher(files);

            reader.read(files.toArray(new File[]{}));

        } else {
            LOGGER.error("Error while scanning, scan-path property must point to a valid directory.");
        }

    }

    public final void startWatcher(List<File> files) {
        if (watcherInstance == null) {
            if (lyreProperties.isEnableLiveReload()) {
                watcher = new Watcher(this, files, lyreProperties);
                watcherInstance = new Thread(watcher);
                watcherInstance.start();
            } else {
                LOGGER.info("Watcher disabled, " +
                    "to enable live-reload feature set 'lyre.enable-live-reload=true' on properties");
            }
        }
    }

    private List<File> searchFiles(File[] fileList) {

        for (File file : fileList) {

            if (file.isFile()
                && !file.getName().equals(LyreProperties.BUNDLE_FILENAME)
                && file.getName().endsWith(lyreProperties.getFileFormat()))
                files.add(file);
            else if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null) searchFiles(files);
            }

        }

        return files;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public LyreProperties getLyreProperties() {
        return lyreProperties;
    }

    public void setLyreProperties(LyreProperties lyreProperties) {
        this.lyreProperties = lyreProperties;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public final Thread getWatcherInstance() {
        return watcherInstance;
    }

    public final Watcher getWatcher() {
        return watcher;
    }
}
