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

import com.github.groovylabs.lyre.domain.exceptions.EndpointNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class Bundle {

    private List<Endpoint> endpoints = new ArrayList<>();

    public Bundle() {

    }

    public List<Endpoint> getEndpoints() {
        return endpoints;
    }

    public void add(Endpoint endpoint) {
        if (endpoint != null)
            endpoints.add(endpoint);
    }

    public void remove(String method, String path) {
        endpoints.removeIf(endpoint -> endpoint.getMethod().name().equals(method) && endpoint.getPath().equals(path));
    }

    public void addAll(List<Endpoint> endpoints) {
        if (endpoints != null) {
            this.endpoints.addAll(endpoints);
        }
    }

    public boolean exists(Endpoint endpoint) {
        return endpoints.stream().anyMatch(e ->
            e.getPath().equals(endpoint.getPath()) && e.getMethod().equals(endpoint.getMethod())
        );
    }

    public Endpoint find(String method, String path) {
        return endpoints.stream()
            .filter(e -> e.getMethod().name().equals(method) && e.getPath().equals(path)).findFirst().orElse(null);
    }

    public void update(Endpoint endpoint) {
        if (exists(endpoint)) {
            endpoints.removeIf(e -> e.getPath().equals(endpoint.getPath()) && e.getMethod().equals(endpoint.getMethod()));
            endpoints.add(endpoint);
        } else
            throw new EndpointNotFoundException("Endpoint [" + endpoint.getMethod() + " - " + endpoint.getPath() + "] not found at the Bundle list!");
    }

    public boolean isEmpty() {
        return endpoints.isEmpty();
    }

    public void clear() {
        endpoints.clear();
    }

}
