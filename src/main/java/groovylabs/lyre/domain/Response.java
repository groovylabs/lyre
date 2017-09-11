package groovylabs.lyre.domain;

import org.springframework.http.HttpStatus;

public class Response {

    private HttpStatus status;

    private String data;

    private String produces = "*/*";

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

    public String getProduces() {
        return produces;
    }

    public void setProduces(String produces) {
        this.produces = produces;
    }
}
