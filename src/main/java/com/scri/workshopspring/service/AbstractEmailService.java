package com.scri.workshopspring.service;

import com.scri.workshopspring.domain.Cliente;
import com.scri.workshopspring.domain.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;


public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")
    private String sender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendOrderConfirmationEmail(Pedido obj){
        SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(obj.getCliente().getEmail());
        sm.setFrom(sender);
        sm.setSubject("Pedido confirmado! Codigo: " + obj.getId());
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText(obj.toString());
        return sm;
    }

    @Override
    public void sendNewPasswordEmail(Cliente cliente, String newPass){
        SimpleMailMessage sm = prepareNewPasswordEmail(cliente, newPass);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPass) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(cliente.getEmail());
        sm.setFrom(sender);
        sm.setSubject("Solicitação de nova senha");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText("Prezado(a)" + cliente.getName() + "\n\n" + "Sua nova senha como solicitado é: "  + newPass + "\n\n" + "Se você não solicitou essa redifinição de senha, entrar em contato imediatamente com " + sender);
        return sm;
    }

    protected String htmlFromTemplatePedido(Pedido obj){
        Context context = new Context();
        context.setVariable("pedido", obj);
        return templateEngine.process("confirmacaoPedido", context);
    }

//    //test//
//    protected String htmlFromTemplateSenha(Cliente obj){
//        Context context = new Context();
//        context.setVariable("cliente", obj);
//        return templateEngine.process("esqueciSenha", context);
//    }

    @Override
    public void sendOrderConfirmationHtmlEmail(Pedido obj){
        try {
            MimeMessage mm = prepareMimeMessageFromPedido(obj);
            sendHtmlEmail(mm);
        }
        catch(MessagingException e){
            sendOrderConfirmationEmail(obj);
        }
    }

    protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
         MimeMessage mimeMessage = javaMailSender.createMimeMessage();
         MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
         mmh.setTo(obj.getCliente().getEmail());
         mmh.setFrom(sender);
         mmh.setSubject("Pedido confirmado! Codigo: " + obj.getId());
         mmh.setSentDate(new Date(System.currentTimeMillis()));
         mmh.setText(htmlFromTemplatePedido(obj), true);
         return mimeMessage;
    }

//    protected MimeMessage prepareMimeMessageFromSenha(Cliente obj, String newPassword) throws MessagingException {
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
//        mmh.setTo(obj.getEmail());
//        mmh.setFrom(sender);
//        mmh.setSubject("Redefinição de Senha");
//        mmh.setText(htmlFromTemplateSenha(obj), true);
//        return mimeMessage;
//    }

}
