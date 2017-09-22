package groovylabs.lyre.engine.scanner;

import groovylabs.lyre.config.LyreProperties;
import groovylabs.lyre.engine.reader.Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class Scanner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Scanner.class);

    @Autowired
    private Reader reader;

    @Autowired
    private LyreProperties lyreProperties;

    private List<File> files = new ArrayList<>();

    @PostConstruct
    public void Scanner() {
        scan();
    }

    public void scan() {

        LOGGER.info("Scanning [*{}] files on path: [{}]",
            lyreProperties.getFileFormat(), lyreProperties.getScanPath());

        File folder = new File(lyreProperties.getScanPath());
        File[] fileList = folder.listFiles();

        searchFiles(fileList);

        if (lyreProperties.isEnableLivereload()) {
            Thread watcher = new Thread(new Watcher(this, files, lyreProperties));
            watcher.start();
        } else {
            LOGGER.info("Watcher disabled, " +
                "to enable live-reload feature set 'lyre.enable-livereload=true' on properties");
        }

        reader.read(files.toArray(new File[]{}));
    }

    private void searchFiles(File[] fileList) {

        for (File file : fileList) {

            if (file.isFile() && file.getName().endsWith(lyreProperties.getFileFormat()))
                files.add(file);
            else if (file.isDirectory())
                searchFiles(file.listFiles());

        }
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }
}
