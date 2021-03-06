package com.scri.workshopspring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;


public class SmtpEmailService extends AbstractEmailService {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private JavaMailSender javaMailSender;

    private static final Logger Log = LoggerFactory.getLogger(SmtpEmailService.class);


    @Override
    public void sendEmail(SimpleMailMessage msg) {
        Log.info("Enviando Email.....");
        mailSender.send(msg);
        Log.info("Email enviado!");
    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        Log.info("Enviando email HTML.....");
        javaMailSender.send(msg);
        Log.info("Email enviado!");
    }
}
