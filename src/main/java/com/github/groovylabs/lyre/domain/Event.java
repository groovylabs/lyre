package com.github.groovylabs.lyre.domain;

import com.github.groovylabs.lyre.domain.enums.EventAction;
import com.github.groovylabs.lyre.domain.enums.Queue;

public class Event<T> {

    private Queue queue;

    private EventAction action;

    private T source;

    public Event(Queue queue, EventAction action) {
        this(queue, action, null);
    }

    public Event(Queue queue, EventAction action, T source) {
        this.queue = queue;
        this.action = action;
        this.source = source;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public EventAction getAction() {
        return action;
    }

    public void setAction(EventAction action) {
        this.action = action;
    }

    public T getSource() {
        return source;
    }

    public void setSource(T source) {
        this.source = source;
    }
}
