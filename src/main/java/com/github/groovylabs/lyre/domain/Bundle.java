package com.github.groovylabs.lyre.domain;

import com.github.groovylabs.lyre.domain.exceptions.EndpointNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    public boolean exists(Endpoint endpoint) {
        return endpoints.stream().anyMatch(e ->
            e.getPath().equals(endpoint.getPath()) && e.getMethod().equals(endpoint.getMethod())
        );
    }

    public void change(Endpoint endpoint) throws EndpointNotFoundException {
        if (exists(endpoint)) {
            endpoints.removeIf(e -> e.getPath().equals(endpoint.getPath()) && e.getMethod().equals(endpoint.getMethod()));
            endpoints.add(endpoint);
        } else
            throw new EndpointNotFoundException("Endpoint [" + endpoint.getMethod() + " - " + endpoint.getPath() + "] not found at the Bundle list!");
    }

}
