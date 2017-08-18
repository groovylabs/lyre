package io.groovelabs.lyre.engine.interpreter;

public enum Level {

    ENDPOINT,
    REQUEST(Property.VALUE, Property.METHOD, Property.CONSUMES, Property.RESPONSE),
    RESPONSE(Property.STATUS, Property.HEADER, Property.DATA, Property.COOKIE),
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
