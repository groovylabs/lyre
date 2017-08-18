package io.groovelabs.lyre.domain;

import org.springframework.http.HttpMethod;

import javax.ws.rs.core.Cookie;

public class Endpoint {

    private HttpMethod method;

    private String path;

    private String data;

    private Cookie cookie;

    private Response response;

    private String fileName;

    public Endpoint() {
        this.setResponse(new Response());
    }

    public void setMethod(String method) {
        this.setMethod(HttpMethod.resolve(method));
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Cookie getCookie() {
        return cookie;
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
