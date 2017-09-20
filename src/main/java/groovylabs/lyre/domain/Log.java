package groovylabs.lyre.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import groovylabs.lyre.domain.enums.EventAction;
import groovylabs.lyre.domain.enums.Queue;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log<T> {

    private String message = "";

    private String level;

    private String timestamp;

    private T target;

    @JsonIgnore
    private Object parameters[];

    public Log() {
    }

    public Log(String message) {
        this.message = message;
    }

    public final void build() {

        this.timestamp = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss  ").format(new Date());
        this.addMessage(timestamp);

        if (parameters != null)
            for (Object parameter : parameters) {
                if (parameter instanceof HttpServletRequest) {
                    this.addMessage(((HttpServletRequest) parameter).getRemoteAddr() + "  ");
                }
            }
    }

    @JsonIgnore
    @SuppressWarnings("unchecked")
    public final Event<T> event() {
        return new Event(Queue.LOG, EventAction.NEW, this);
    }

    @JsonIgnore
    public final Log info(String message) {
        this.addMessage("INFO ");
        this.addMessage(message);
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void addMessage(String message) {
        this.message = this.message.concat(message);
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

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
