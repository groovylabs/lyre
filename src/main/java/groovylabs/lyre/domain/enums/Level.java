package groovylabs.lyre.domain.enums;

public enum Level {
    INFO,
    WARN,
    ERROR;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

}
