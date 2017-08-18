package io.groovelabs.lyre.engine.interpreter;

public enum Property {

    VALUE, METHOD, COOKIE, PARAMS, RESPONSE, STATUS, CONSUMES, PRODUCES, HEADER, DATA;

    public boolean is(String property) {
        return this.name().equalsIgnoreCase(property);
    }

}
