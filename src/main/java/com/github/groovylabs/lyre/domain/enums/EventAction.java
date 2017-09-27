package com.github.groovylabs.lyre.domain.enums;

public enum EventAction {
    NEW,
    UPDATE,
    DELETED;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
