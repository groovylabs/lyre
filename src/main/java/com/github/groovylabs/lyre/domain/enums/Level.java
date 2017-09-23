package com.github.groovylabs.lyre.domain.enums;

public enum Level {

    ENDPOINT,
    REQUEST(Property.VALUE, Property.METHOD, Property.CONSUMES, Property.RESPONSE),
    RESPONSE(Property.STATUS, Property.HEADER, Property.PRODUCES, Property.DATA),
    SETUP(Property.IDLE, Property.BUSY, Property.BROKEN, Property.FORBIDDEN),
    PARAMETER();

    private Property[] properties;

    Level(Property... properties) {
        this.properties = properties;
    }

    public Property has(String property) {
        for (Property prop : properties) {
            if (prop.is(property))
                return prop;
        }
        return null;
    }

}
