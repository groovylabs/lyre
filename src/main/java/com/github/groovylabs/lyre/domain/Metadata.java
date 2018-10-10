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

import java.util.HashMap;
import java.util.Map;

public class Metadata {

    private String version;

    private Map<String, Long> modified;

    private Map<String, String> revision;

    public Metadata() {
        this.modified = new HashMap<>();
        this.revision = new HashMap<>();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, Long> getModified() {
        return modified;
    }

    public Map<String, String> getRevision() {
        return revision;
    }

    public boolean containtsModified(Endpoint endpoint) {
        return this.modified.containsKey(endpoint.getMethod() + " " + endpoint.getPath());
    }

    public void modifiedAt(Endpoint endpoint) {
        this.modifiedAt(endpoint.getMethod() + " " + endpoint.getPath(), endpoint.getLastModified());
    }

    public void modifiedAt(String endpointAlias, long timestamp) {
        modified.put(endpointAlias, timestamp);
    }

    public void putRevision(Endpoint endpoint) {
        this.revision.put(endpoint.getMethod() + " " + endpoint.getPath(), endpoint.getHash());
    }

    public void putRevision(String key, String revision) {
        this.revision.put(key, revision);
    }

    public String revision(String key) {
        return this.revision.get(key);
    }
}
