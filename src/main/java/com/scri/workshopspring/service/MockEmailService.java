package com.scri.workshopspring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;


import javax.mail.internet.MimeMessage;


public class MockEmailService extends AbstractEmailService {

    private static final Logger Log = LoggerFactory.getLogger(MockEmailService.class);


    @Override
    public void sendEmail(SimpleMailMessage msg) {
        Log.info("Simulando envio do email....");
        Log.info(msg.toString());
        Log.info("Email enviado!");
    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        Log.info("Simulando envio do email HTML....");
        Log.info(msg.toString());
        Log.info("Email enviado!");
    }
}
