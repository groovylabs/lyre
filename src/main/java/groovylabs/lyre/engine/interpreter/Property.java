package groovylabs.lyre.engine.interpreter;

public enum Property {

    VALUE, METHOD, PARAMS, RESPONSE, STATUS, CONSUMES, PRODUCES, HEADER, DATA,
    SETUP, IDLE, BUSY, BROKEN, FORBIDDEN;

    public boolean is(String property) {
        return this.name().equalsIgnoreCase(property);
    }

}
