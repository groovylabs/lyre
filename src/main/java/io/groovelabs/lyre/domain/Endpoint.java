package io.groovelabs.lyre.domain;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import javax.ws.rs.core.Cookie;

public class Endpoint {

    private HttpMethod method;

    private String path;

    private HttpStatus status;

    private String data;

    private Cookie cookie;

    public Endpoint() {

    }

    public void setMethod(String method) {
        this.setMethod(HttpMethod.resolve(method));
    }

    public void setStatus(String status) {
        this.setStatus(HttpStatus.valueOf(Integer.parseInt(status)));
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

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
