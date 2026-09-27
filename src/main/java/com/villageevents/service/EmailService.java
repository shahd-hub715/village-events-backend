package com.villageevents.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;

@Service
public class EmailService {

    private final Resend resend;
    private final String adminEmail;

    public EmailService(
            @Value("${resend.api-key}") String apiKey,
            @Value("${APP_ADMIN_EMAIL}") String adminEmail) {
        this.resend = new Resend(apiKey);
        this.adminEmail = adminEmail;
    }

    public void sendEmail(String subject, String html) {
        CreateEmailOptions email = CreateEmailOptions.builder()
                .from("مناسباتنا <notifications@monasbatna.com>")
                .to(adminEmail)
                .subject(subject)
                .html(html)
                .build();

        try {
            resend.emails().send(email);
        } catch (ResendException e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}