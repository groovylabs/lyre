package io.groovelabs.lyre.interpreter;

public enum Level {

    ENDPOINT(1), RESPONSE(1), PROPERTY(2), PARAMETER(3);

    private final Integer index;

    private Integer index() {
        return this.index;
    }

    Level(Integer index) {
        this.index = index;
    }

}
