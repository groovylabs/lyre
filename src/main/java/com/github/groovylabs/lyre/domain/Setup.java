package com.github.groovylabs.lyre.domain;

import com.github.groovylabs.lyre.domain.appliers.Countdown;

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
