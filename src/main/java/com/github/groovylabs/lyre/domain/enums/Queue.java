package com.github.groovylabs.lyre.domain.enums;

public enum Queue {
    BUNDLE,
    LOG;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
