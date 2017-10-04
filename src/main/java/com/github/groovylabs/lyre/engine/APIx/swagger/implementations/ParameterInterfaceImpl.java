package com.github.groovylabs.lyre.engine.APIx.swagger.implementations;

import io.swagger.models.parameters.Parameter;

import java.util.Map;

public class ParameterInterfaceImpl implements Parameter {

    private String in;
    private String access;
    private String name;
    private String description;
    private boolean required = false;
    private String pattern;

    private Map<String, Object> vendorExtensions;

    private Boolean readOnly = false;
    private Boolean allowEmptyValue = false;

    public ParameterInterfaceImpl() {}


    @Override
    public String getIn() {
        return this.in;
    }

    @Override
    public void setIn(String in) {
        this.in = in;
    }

    @Override
    public String getAccess() {
        return this.access;
    }

    @Override
    public void setAccess(String access) {
        this.access = access;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;

    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean getRequired() {
        return this.required;
    }

    @Override
    public void setRequired(boolean required) {
        this.required = required;
    }

    @Override
    public String getPattern() {
        return this.pattern;
    }

    @Override
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Map<String, Object> getVendorExtensions() {
        return this.vendorExtensions;
    }

    @Override
    public Boolean isReadOnly() {
        return this.readOnly;
    }

    @Override
    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    @Override
    public Boolean getAllowEmptyValue() {
        return this.allowEmptyValue;
    }

    @Override
    public void setAllowEmptyValue(Boolean allowEmptyValue) {
        this.allowEmptyValue = allowEmptyValue;
    }
}
