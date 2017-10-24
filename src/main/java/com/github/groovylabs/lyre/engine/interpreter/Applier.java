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

package com.github.groovylabs.lyre.engine.interpreter;

import com.github.groovylabs.lyre.domain.Endpoint;
import com.github.groovylabs.lyre.domain.Level;
import com.github.groovylabs.lyre.domain.appliers.Countdown;
import com.github.groovylabs.lyre.domain.interfaces.ApplyOn;
import org.springframework.http.HttpStatus;

public enum Applier implements ApplyOn<Endpoint, String> {

    NONE,
    METHOD {
        @Override
        public void apply(Endpoint endpoint, String value) {
            endpoint.setMethod(value);
        }
    },
    PATH {
        @Override
        public void apply(Endpoint endpoint, String value) {
            endpoint.setPath(value);
        }
    },
    ALIAS {
        @Override
        public void apply(Endpoint endpoint, String value) {
            endpoint.setAlias(value);
        }
    },
    NAME {
        @Override
        public void apply(Endpoint endpoint, String value) {
            ALIAS.apply(endpoint, value);
        }
    },
    CONSUMES {
        @Override
        public void apply(Endpoint endpoint, String value) {
            endpoint.setConsumes(value);
        }
    },
    STATUS {
        @Override
        public void apply(Endpoint endpoint, String value) {
            endpoint.getResponse().setStatus(value);
        }
    },
    PRODUCES {
        @Override
        public void apply(Endpoint endpoint, String value) {
            endpoint.getResponse().setProduces(value);
        }
    },
    HEADER {
        @Override
        public void apply(Endpoint endpoint, String value) {
            if (!super.key.equals(Level.HEADER.name().toLowerCase())) {
                if (super.level.equals(Level.REQUEST))
                    endpoint.getHeader().addContent(super.key, value);
                else if (super.level.equals(Level.RESPONSE)) {
                    endpoint.getResponse().getHeader().addContent(super.key, value);
                }
            }
        }
    },
    DATA {
        @Override
        public void apply(Endpoint endpoint, String value) {
            if (super.level.equals(Level.REQUEST))
                endpoint.setData(value);
            else if (super.level.equals(Level.RESPONSE)) {
                endpoint.getResponse().setData(value);
            }
        }
    },
    IDLE {
        @Override
        public void apply(Endpoint endpoint, String value) {
            endpoint.getProperty().getTimer().setIdle(Long.valueOf(value));
        }
    },
    TIMEOUT {
        @Override
        public void apply(Endpoint endpoint, String value) {
            IDLE.apply(endpoint, value);
        }
    },
    BUSY {
        @Override
        public void apply(Endpoint endpoint, String value) {
            endpoint.getProperty().setCountdown(new Countdown(HttpStatus.TOO_MANY_REQUESTS, Long.valueOf(value)));
        }
    },
    BROKEN {
        @Override
        public void apply(Endpoint endpoint, String value) {
            endpoint.getProperty().setCountdown(new Countdown(HttpStatus.INTERNAL_SERVER_ERROR, Long.valueOf(value)));
        }
    },
    FORBIDDEN {
        @Override
        public void apply(Endpoint endpoint, String value) {
            endpoint.getProperty().setCountdown(new Countdown(HttpStatus.FORBIDDEN, Long.valueOf(value)));
        }
    };

    public void apply(Endpoint endpoint, String value) {
    }

    private Level level;

    private String key;

    public Applier set(Level level, String key) {
        this.level = level;
        this.key = key;
        return this;
    }

}
