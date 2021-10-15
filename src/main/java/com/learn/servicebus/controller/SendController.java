package com.learn.servicebus.controller;


import com.learn.servicebus.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendController {

    @Autowired
    private JmsTemplate jmsTemplate;

    private static final Logger logger = LoggerFactory.getLogger(SendController.class);


    @GetMapping("/message")
    public String postMessage() {
        logger.info("Sending message");
        jmsTemplate.convertAndSend(new User("Welcome back Service Bus"));
        return "ss";
    }
}
