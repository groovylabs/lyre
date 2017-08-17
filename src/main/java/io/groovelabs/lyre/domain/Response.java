package io.groovelabs.lyre.domain;

import org.springframework.http.HttpStatus;

public class Response {

    private HttpStatus status;

    private String data;

    public void setStatus(String status) {
        this.setStatus(HttpStatus.valueOf(Integer.parseInt(status)));
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
