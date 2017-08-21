package io.groovelabs.lyre.domain.appliers;

import org.springframework.http.HttpStatus;

public class Countdown {

    private HttpStatus status;

    private long calls;

    public Countdown(HttpStatus status, long calls) {
        this.status = status;
        this.calls = calls;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public long getCalls() {
        return calls;
    }

    public void setCalls(long calls) {
        this.calls = calls;
    }

    public void decrease() {
        this.calls--;
    }
}
