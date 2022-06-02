package com.prepfortech.accessor;

import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class EmailAccessor {

    @Autowired
    Mailer mailer;

    @Qualifier("fromEmail")
    @Autowired
    private String fromEmail;

    @Qualifier("fromName")
    @Autowired
    private String fromName;

    public void sendEmail(final String toName, final String toEmail,
                          final String subject, final String contents) {
        Email email = EmailBuilder
                .startingBlank()
                .to(toName, toEmail)
                .from(fromName, fromEmail)
                .withSubject(subject)
                .withPlainText(contents)
                .buildEmail();

        mailer.sendMail(email);
    }
}
