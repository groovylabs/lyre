package io.groovelabs.lyre.engine.scanner;

import io.groovelabs.lyre.engine.Overlay;
import io.groovelabs.lyre.config.ScannerProperties;
import io.groovelabs.lyre.engine.reader.Reader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Scanner extends Overlay<Reader> {

    private List<File> files = new ArrayList<>();

    public Scanner(Reader reader) {
        super(reader);
        scan();
    }

    public void scan() {
        System.out.println("resources path = " + ScannerProperties.path);

        File folder = new File(ScannerProperties.path);
        File[] listOfFiles = folder.listFiles();

        recursiveSearchLyreFile(listOfFiles);

        Thread checkLyreFiles = new Thread(new Watcher(this, files));
        checkLyreFiles.start();

        overlay().read(files.toArray(new File[]{}));
    }

    private void recursiveSearchLyreFile(File[] listOfFiles) {
        for (File fileOrFolder : listOfFiles) {
            if (fileOrFolder.isFile() && fileOrFolder.getName().endsWith(".lyre"))
                files.add(fileOrFolder);
            else if (fileOrFolder.isDirectory())
                recursiveSearchLyreFile(fileOrFolder.listFiles());
        }
    }
}
