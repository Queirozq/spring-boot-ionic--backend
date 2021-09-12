package com.scri.workshopspring.service;

import com.scri.workshopspring.domain.Cliente;
import com.scri.workshopspring.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;


public interface EmailService {

       void sendOrderConfirmationEmail(Pedido obj);

       void sendEmail(SimpleMailMessage msg);

       void sendOrderConfirmationHtmlEmail(Pedido obj);

       void sendHtmlEmail(MimeMessage msg);

       void sendNewPasswordEmail(Cliente cliente, String newPass);
}
