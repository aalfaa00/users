package com.zeromax.users.service;

import com.zeromax.users.utils.Constants;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    private final JavaMailSender mailSender;

    public EmailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(String mail, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(Constants.MAIL);
        message.setTo(mail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
