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

package com.github.groovylabs.lyre.engine.apix.websocket;

import com.github.groovylabs.lyre.domain.Endpoint;
import com.github.groovylabs.lyre.domain.Event;
import com.github.groovylabs.lyre.domain.Log;
import com.github.groovylabs.lyre.domain.enums.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
public class Dispatcher {

    private String queuePrefix = "/registry/";

    private MessageSendingOperations<String> messagingTemplate;

    @Autowired
    public Dispatcher(MessageSendingOperations<String> messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void publish(Event<?> event) {

        if (event != null && event.getQueue() != null && event.getAction() != null) {

            String queue = "";

            if (event.getQueue().equals(Queue.BUNDLE)) {
                queue = queuePrefix + event.getQueue();
            } else if (event.getQueue().equals(Queue.LOG) &&
                ((Log) event.getSource()).getTarget() instanceof Endpoint) {
                queue = queuePrefix + event.getQueue() + "/" + ((Endpoint) ((Log) event.getSource()).getTarget()).getHash();
            }

            this.messagingTemplate.convertAndSend(queue, event);

        }

    }
}
