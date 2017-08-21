package io.groovelabs.lyre.domain.appliers;

public class Timer {

    private long idle;

    public Timer() {
        setIdle(0L);
    }

    public long idle() {
        return idle;
    }

    public long getIdle() {
        return idle;
    }

    public void setIdle(long idle) {
        this.idle = idle;
    }
}
