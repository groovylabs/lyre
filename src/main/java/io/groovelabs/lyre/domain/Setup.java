package io.groovelabs.lyre.domain;

import io.groovelabs.lyre.domain.appliers.Countdown;

public class Setup {

    private Countdown countdown;

    public Setup() {
    }

    public Countdown getCountdown() {
        return countdown;
    }

    public void setCountdown(Countdown countdown) {
        this.countdown = countdown;
    }
}
