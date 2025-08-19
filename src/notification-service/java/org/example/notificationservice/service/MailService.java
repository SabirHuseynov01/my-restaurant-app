package org.example.notificationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;

    public void sendMail(String text){
        var simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("huseynovsabir61@gmail.com");
        simpleMailMessage.setTo("huseynovsabir8569@gmail.com");
        simpleMailMessage.setSubject("Notification Service");
        simpleMailMessage.setText(text);

        javaMailSender.send(simpleMailMessage);

    }
}
