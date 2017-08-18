package io.groovelabs.lyre.domain;

import io.groovelabs.lyre.domain.enums.FileType;

import java.io.File;

public class LyreFile {

    private File file;

    private FileType fileType;

    public LyreFile(File file, FileType fileType) {
        this.file = file;
        this.fileType = fileType;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }
}
