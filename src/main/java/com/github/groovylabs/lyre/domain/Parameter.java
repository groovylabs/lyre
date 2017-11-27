package com.github.groovylabs.lyre.domain;

import java.util.HashMap;
import java.util.Map;

public class Parameter {

    private final static String DIVIDER_QUERY_PARAM = "&";

    public final static String START_QUERY_PARAM = "?";
    public final static String EQUAL_QUERY_PARAM = "=";

    private Map<String, String> queryParam;

    private Map<String, String> pathParam;

    public Parameter() {
        this.setQueryParam(new HashMap<>());
        this.setPathParam(new HashMap<>());
    }

    public Map<String, String> getQueryParam() {
        return queryParam;
    }

    public void setQueryParam(Map<String, String> queryParam) {
        this.queryParam = queryParam;
    }

    public Map<String, String> getPathParam() {
        return pathParam;
    }

    public void setPathParam(Map<String, String> pathParam) {
        this.pathParam = pathParam;
    }

    // TODO: Accept "?" when use on value.
    public void composeQueryParam(String word) {

        if (word.contains(DIVIDER_QUERY_PARAM))
            composeQueryparam(word.split(DIVIDER_QUERY_PARAM));


        if (word.contains(EQUAL_QUERY_PARAM)) {
            String[] queryParam = word.split(EQUAL_QUERY_PARAM);

            this.queryParam.put(queryParam[0], queryParam[1]);
        }
    }

    private void composeQueryparam(String[] words) {
        for (String word : words) composeQueryParam(word);
    }
}
