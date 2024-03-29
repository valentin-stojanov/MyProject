package com.myproject.project.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void sendRegistrationEmail(String userEmail, String firstName){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("routetales");
            mimeMessageHelper.setTo(userEmail);
            mimeMessageHelper.setSubject("Welcome to RouteTales.eu");
            mimeMessageHelper.setText(generateRegistrationMessageContent(firstName), true);

            this.javaMailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendResetPasswordEmail(String email, String resetUrl) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("routetales.eu");
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("Reset password RouteTales.eu");
            mimeMessageHelper.setText(generateResetPasswordMessageContent(email, resetUrl), true);

            this.javaMailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateRegistrationMessageContent(String firstName) {
        Context ctx = new Context();
        ctx.setVariable("firstName", firstName);

        return this.templateEngine.process("email/registration", ctx);
    }

    private String generateResetPasswordMessageContent(String email, String resetUrl) {
        Context ctx = new Context();
        ctx.setVariable("email", email);
        ctx.setVariable("resetUrl", resetUrl);
        return this.templateEngine.process("email/reset-email", ctx);
    }
}
