package io.groovelabs.lyre.domain;

import org.springframework.http.HttpMethod;

import javax.ws.rs.core.Cookie;

public class Endpoint {

    private HttpMethod method;

    private String path;

    private String consumes = "*/*";

    private Cookie cookie;

    private long idle = -1;

    private Response response;

    private Setup setup;

    private String fileName;

    public Endpoint() {
        this.setResponse(new Response());
        this.setSetup(new Setup());
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

    public String getConsumes() {
        return consumes;
    }

    public void setConsumes(String consumes) {
        this.consumes = consumes;
    }

    public Cookie getCookie() {
        return cookie;
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    public long getIdle() {
        return idle;
    }

    public void setIdle(long idle) {
        this.idle = idle;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Setup getSetup() {
        return setup;
    }

    public void setSetup(Setup setup) {
        this.setup = setup;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
