package com.politechnika.notification.service;

public interface EmailService {

    void sendEmail(String to, String subject, String content);
}
