package io.groovelabs.lyre.engine.scanner;

import io.groovelabs.lyre.engine.Overlay;
import io.groovelabs.lyre.config.ScannerProperties;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class Watcher extends Overlay<Scanner> implements Runnable {

    private final Path path = FileSystems.getDefault().getPath(ScannerProperties.path);

    private List<File> files;

    public Watcher(Scanner scanner, List<File> files) {
        super(scanner);

        this.files = files;
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
                        for (File file : files) {
                            if (file.getName().equals(changedFile.getName())) {
                                System.out.println(" CHANGED ");
                                overlay().overlay().read(file);

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
