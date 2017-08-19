package io.groovelabs.lyre.domain;

import io.groovelabs.lyre.domain.setups.Countdown;

public class Setup {

    private long idle;

    private Countdown countdown;

    public Setup() {
        idle = -1;
    }

    public long getIdle() {
        return idle;
    }

    public void setIdle(long idle) {
        this.idle = idle;
    }

    public Countdown getCountdown() {
        return countdown;
    }

    public void setCountdown(Countdown countdown) {
        this.countdown = countdown;
    }
}
