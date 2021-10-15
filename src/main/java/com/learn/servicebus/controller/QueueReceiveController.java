package com.learn.servicebus.controller;

import com.learn.servicebus.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class QueueReceiveController {
    private static final String QUEUE_NAME = "test-queue-jms";

    private final Logger logger = LoggerFactory.getLogger(QueueReceiveController.class);

    @JmsListener(destination = QUEUE_NAME, containerFactory = "listenerQeueFactory")
    public void receiveQueueMessage(User user) {
        logger.info("Receiving message from queue: {}", user.getName());
    }
}
