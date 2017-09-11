package groovylabs.lyre.engine.scanner;

import groovylabs.lyre.config.ScannerProperties;
import groovylabs.lyre.engine.Overlay;
import groovylabs.lyre.engine.reader.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class Scanner {

    private List<File> files = new ArrayList<>();

    @Autowired
    private Reader reader;

    @PostConstruct
    public void Scanner() {
        scan();
    }

    public void scan() {
        System.out.println("resources path = " + ScannerProperties.path);

        File folder = new File(ScannerProperties.path);
        File[] fileList = folder.listFiles();

        searchFiles(fileList);

        Thread checkLyreFiles = new Thread(new Watcher(this, files));
        checkLyreFiles.start();

        reader.read(files.toArray(new File[]{}));
    }

    private void searchFiles(File[] listOfFiles) {

        for (File fileOrFolder : listOfFiles) {

            if (fileOrFolder.isFile() && fileOrFolder.getName().endsWith(".lyre"))
                files.add(fileOrFolder);
            else if (fileOrFolder.isDirectory())
                searchFiles(fileOrFolder.listFiles());

        }
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }
}
