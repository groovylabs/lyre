package com.github.groovylabs.lyre.domain.enums;

public enum LogLevel {
    INFO,
    WARN,
    ERROR;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

}
