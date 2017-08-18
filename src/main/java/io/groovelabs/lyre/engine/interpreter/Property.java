package io.groovelabs.lyre.engine.interpreter;

public enum Property {

    VALUE, METHOD, DATA, COOKIE, PARAMS, RESPONSE, STATUS ;

    public boolean is(String property) {
        return this.name().equalsIgnoreCase(property);
    }

}
