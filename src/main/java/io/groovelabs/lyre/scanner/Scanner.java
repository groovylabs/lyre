package io.groovelabs.lyre.scanner;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class Scanner {

    private final static String pathLyreFiles = System.getProperty("user.dir") + "/src/main/resources";
    private List<File> lyreFiles = new ArrayList<>();

    public List<File> scan() {
        System.out.println("pathLyreFiles = " + pathLyreFiles);

        File folder = new File(pathLyreFiles);
        File[] listOfFiles = folder.listFiles();

        recursiveSearchLyreFile(listOfFiles);

        return lyreFiles;
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
