package groovylabs.lyre.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import groovylabs.lyre.domain.enums.EventAction;
import groovylabs.lyre.domain.enums.Queue;

public class Log<T> {

    private String message;

    private String level;

    private T target;

    public Log() {

    }

    public Log(String message) {
        this.message = message;
    }

    @JsonIgnore
    public final Event event() {
        return new Event(Queue.LOG, EventAction.NEW, getTarget());
    }

    @JsonIgnore
    public final Log info(String message) {
        this.setMessage(message);
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public T getTarget() {
        return target;
    }

    public void setTarget(T target) {
        this.target = target;
    }
}
