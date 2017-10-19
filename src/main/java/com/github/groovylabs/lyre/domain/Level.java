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

import com.github.groovylabs.lyre.domain.errors.SyntaxError;
import com.github.groovylabs.lyre.domain.interfaces.ApplyOn;

public enum Level {

    ENDPOINT,
    HEADER(Syntax.CUSTOM),
    REQUEST(
        Syntax.METHOD, Syntax.PATH, Syntax.ALIAS, Syntax.NAME, Syntax.CONSUMES,
        Syntax.DATA, Syntax.HEADER, Syntax.RESPONSE, Syntax.PROPERTY
    ),
    RESPONSE(Syntax.STATUS, Syntax.HEADER, Syntax.PRODUCES, Syntax.DATA),
    PROPERTY(Syntax.IDLE, Syntax.TIMEOUT, Syntax.BUSY, Syntax.BROKEN, Syntax.FORBIDDEN);

    private Syntax[] syntaxes;

    Level(Syntax... syntaxes) {
        this.syntaxes = syntaxes;
    }

    public ApplyOn<Endpoint, String> has(String syntax) throws SyntaxError {
        return this.has(syntax, syntax);
    }

    public ApplyOn<Endpoint, String> has(Syntax syntax) throws SyntaxError {
        return this.has(syntax.name(), syntax.name());
    }

    public ApplyOn<Endpoint, String> has(Syntax syntax, String key) throws SyntaxError {
        return this.has(syntax.name(), key);
    }

    public ApplyOn<Endpoint, String> has(String syntax, String key) throws SyntaxError {
        for (Syntax obj : syntaxes) {
            if (obj.is(syntax))
                return obj.applier(this, key);
            else if (obj.equals(Syntax.CUSTOM))
                return Syntax.valueOf(syntax).applier(this, key);
        }
        throw new SyntaxError();
    }

}
