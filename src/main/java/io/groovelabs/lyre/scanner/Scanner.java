package io.groovelabs.lyre.scanner;

import io.groovelabs.lyre.domain.LyreFile;
import io.groovelabs.lyre.domain.enums.FileType;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class Scanner {

    private final static String pathLyreFiles = System.getProperty("user.dir") + "/src/main/resources";
    private List<LyreFile> lyreFiles = new ArrayList<>();

    public List<LyreFile> scan() {
        System.out.println("pathLyreFiles = " + pathLyreFiles);

        File folder = new File(pathLyreFiles);
        File[] listOfFiles = folder.listFiles();

        recursiveSearchLyreFile(listOfFiles);

        Thread checkLyreFiles = new Thread(new CheckLyreFilesThread(lyreFiles));
        checkLyreFiles.start();

        return lyreFiles;
    }

    private void recursiveSearchLyreFile(File[] listOfFiles) {

        for (File fileOrFolder : listOfFiles) {
            if (fileOrFolder.isFile() && fileOrFolder.getName().endsWith(".lyre")) {

                java.util.Scanner scanner = null;
                try {
                    scanner = new java.util.Scanner(new BufferedReader(new FileReader(fileOrFolder.getPath())));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                LyreFile lyreFile;

                char firstChar = scanner.next().charAt(0);
                if (firstChar == '{')
                    lyreFile = new LyreFile(fileOrFolder, FileType.JSON);
                else
                    lyreFile = new LyreFile(fileOrFolder, FileType.YAML);

                lyreFiles.add(lyreFile);
            }
            else if (fileOrFolder.isDirectory())
                recursiveSearchLyreFile(fileOrFolder.listFiles());
        }
    }
}
