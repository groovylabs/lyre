package io.groovelabs.lyre.domain;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public class Endpoint {

    private HttpMethod method;

    private String path;

    private HttpStatus status;

    private String data;

    public Endpoint() {

    }

    // TODO create InvalidHttpMethodException
    public void setMethod(String method) throws Exception {

        this.setMethod(HttpMethod.resolve(method));

        if (method == null)
            throw new Exception();
    }

    // TODO create InvalidHttpStatusException
    public void setStatus(String status) throws Exception {

        this.setStatus(HttpStatus.valueOf(Integer.parseInt(status)));

        if (status == null)
            throw new Exception();
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
