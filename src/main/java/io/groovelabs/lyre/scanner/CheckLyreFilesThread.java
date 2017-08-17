package io.groovelabs.lyre.scanner;

import io.groovelabs.lyre.domain.LyreFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class CheckLyreFilesThread implements Runnable {

    private final static String pathLyreFiles = System.getProperty("user.dir") + "/src/main/resources";
    private final Path path = FileSystems.getDefault().getPath(pathLyreFiles);
    private List<LyreFile> lyreFiles;

    public CheckLyreFilesThread(List<LyreFile> lyreFiles) {
        this.lyreFiles = lyreFiles;
    }

    @Override
    public void run() {
        try (final WatchService watchService = FileSystems.getDefault().newWatchService()) {
            path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

            while (true) {

                final WatchKey wk = watchService.take();

                for (WatchEvent<?> event : wk.pollEvents()) {

                    //we only register "ENTRY_MODIFY" so the context is always a Path.
                    final Path changedPath = (Path) event.context();
                    File changedFile = new File(changedPath.getFileName().toString());
                    System.out.println(changedFile);

                    if (changedFile.getName().endsWith(".lyre")) {
                        for (LyreFile lyreFile : lyreFiles) {
                            if (lyreFile.getFile().getName().equals(changedFile.getName())) {
                                System.out.println("Matched the files, do things with this! =]");
                                break;
                            }
                        }
                    } else {
                        System.out.println("not changed");
                    }
                }

                // reset the key
                wk.reset();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
