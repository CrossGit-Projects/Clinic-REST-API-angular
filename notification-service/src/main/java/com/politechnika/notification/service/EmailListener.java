package com.politechnika.notification.service;

import com.politechnika.notification.model.Notification;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailListener {

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = "232998")
    public void listenerNotification(Notification notification) {
        System.out.println(" Email: " + notification.getEmail()
                + " title: " + notification.getTitle()
                + " body: " + notification.getContent());
        emailService.sendEmail(notification.getEmail(), notification.getTitle(), notification.getContent());
    }
}
