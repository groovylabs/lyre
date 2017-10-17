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

package com.github.groovylabs.lyre.domain.enums;

import com.github.groovylabs.lyre.domain.Endpoint;
import com.github.groovylabs.lyre.domain.interfaces.ApplyOn;
import com.github.groovylabs.lyre.engine.interpreter.Applier;

public enum Syntax {

    // ENDPOINT REQUEST
    METHOD, PATH, ALIAS, NAME, CONSUMES,
    // ENDPOINT RESPONSE
    RESPONSE, RESPONSES, STATUS, PRODUCES,
    // ENDPOINT COMMON
    DATA, HEADER,
    // ENDPOINT PROPERTIES
    PROPERTY, PROPERTIES, IDLE, TIMEOUT, BUSY, BROKEN, FORBIDDEN;

    public boolean is(String property) {
        return this.name().equalsIgnoreCase(property);
    }

    public ApplyOn<Endpoint> applier(Level level) {
        try {
            return Applier.valueOf(this.name()).level(level);
        } catch (IllegalArgumentException | NullPointerException e) {
            return Applier.NONE;
        }
    }

}
