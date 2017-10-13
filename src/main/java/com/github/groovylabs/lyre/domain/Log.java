/*
 * MIT License
 *
 * Copyright (c) 2017 Groovylabs
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.github.groovylabs.lyre.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.groovylabs.lyre.domain.enums.EventAction;
import com.github.groovylabs.lyre.domain.enums.Queue;

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
