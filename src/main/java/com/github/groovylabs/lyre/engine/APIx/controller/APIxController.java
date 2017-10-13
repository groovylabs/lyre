/*
 * MIT License
 *
 * Copyright (c) 2017 Groovylabs
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

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
