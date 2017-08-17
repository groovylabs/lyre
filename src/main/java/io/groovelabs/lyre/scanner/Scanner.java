package io.groovelabs.lyre.scanner;

import io.groovelabs.lyre.APIx.engine.Overlay;
import io.groovelabs.lyre.reader.Reader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Scanner extends Overlay<Reader> {

    private final static String pathLyreFiles = System.getProperty("user.dir") + "/src/main/resources";
    private List<File> lyreFiles = new ArrayList<>();

    public Scanner(Reader reader) {
        super(reader);
        scan();
    }

    public void scan() {
        System.out.println("pathLyreFiles = " + pathLyreFiles);

        File folder = new File(pathLyreFiles);
        File[] listOfFiles = folder.listFiles();

        recursiveSearchLyreFile(listOfFiles);

        Thread checkLyreFiles = new Thread(new CheckLyreFilesThread(lyreFiles));
        checkLyreFiles.start();

        overlay().read(lyreFiles);
    }

    private void recursiveSearchLyreFile(File[] listOfFiles) {
        for (File fileOrFolder : listOfFiles) {
            if (fileOrFolder.isFile() && fileOrFolder.getName().endsWith(".lyre"))
                lyreFiles.add(fileOrFolder);
            else if (fileOrFolder.isDirectory())
                recursiveSearchLyreFile(fileOrFolder.listFiles());
        }
    }
}
