package com.github.groovylabs.lyre.domain.enums;

public enum Property {

    METHOD, PATH, ALIAS, NAME, DATA, IDLE, TIMEOUT, PARAMS, RESPONSE, RESPONSES, STATUS, CONSUMES, PRODUCES, HEADER,
    SETUP, BUSY, BROKEN, FORBIDDEN;

    public boolean is(String property) {
        return this.name().equalsIgnoreCase(property);
    }

}
