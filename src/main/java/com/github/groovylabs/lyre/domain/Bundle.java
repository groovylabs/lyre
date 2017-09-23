package com.github.groovylabs.lyre.domain;

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

}
