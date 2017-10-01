package com.github.groovylabs.lyre.engine.APIx.controller;

import com.github.groovylabs.lyre.engine.APIx.APIx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class APIxController {

    private static final Logger LOGGER = LoggerFactory.getLogger(APIxController.class);

    @Autowired
    private APIx apix;

    private static int running;

    private Timer controller;

    private boolean attempting = false;

    @PostConstruct
    public void APIxController() {
        this.running = 0;
        this.controller = new Timer();
    }

    public void bootAttempt(String source) {
        LOGGER.info("Boot [STATUS]: Attempting to boot from [{}]", source);

        if (!attempting) {
            attempting = true;
            this.delayedBoot();
        }
    }

    private void delayedBoot() {

        controller.cancel();

        if (running == 0) {
            apix.boot();
            attempting = false;
        } else {
            controller = new Timer();
            TimerTask delayedRestart = new TimerTask() {
                @Override
                public void run() {
                    delayedBoot();
                }
            };
            controller.schedule(delayedRestart, 2500);
        }
    }

    void increase() {
        running++;
    }

    void decrease() {
        running--;
    }

}
