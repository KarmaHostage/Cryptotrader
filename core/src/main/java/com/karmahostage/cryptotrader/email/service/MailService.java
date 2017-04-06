package com.karmahostage.cryptotrader.email.service;

import net.sargue.mailgun.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MailService {

    private Log logger = LogFactory.getLog(MailService.class);

    @Autowired
    private Configuration mailConfiguration;

    public Mail createMail() {
        return new Mail();
    }

    private void sendMail(Mail mail) {
        net.sargue.mailgun.Mail.using(mailConfiguration)
                .to(mail.to.orElseThrow(() -> new IllegalArgumentException("Please Provide a target address")))
                .subject(mail.subject.orElse(""))
                .text(mail.body.orElse(""))
                .build()
                .send();
    }

    public class Mail {
        private Optional<String> from = Optional.empty();
        private Optional<String> to = Optional.empty();
        private Optional<String> subject = Optional.empty();
        private Optional<String> body = Optional.empty();
        private Boolean html = false;
        private List<Attachment> attachments = new ArrayList<>();

        public Mail from(String from) {
            this.from = Optional.ofNullable(from);
            return this;
        }

        public Mail to(String to) {
            this.to = Optional.ofNullable(to);
            return this;
        }

        public Mail subject(String subject) {
            this.subject = Optional.ofNullable(subject);
            return this;
        }

        public Mail body(String body) {
            this.body = Optional.ofNullable(body);
            return this;
        }

        public Mail attachment(String name, String contents) {
            attachments.add(new Attachment(name, new ByteArrayResource(contents.getBytes())));
            return this;
        }

        public Mail htmlBody(String body) {
            this.body = Optional.ofNullable(body);
            this.html = true;
            return this;
        }

        public void send() {
            sendMail(this);
            logger.info("mail was sent to " + this.to.orElse("unknown"));
        }
    }

    private class Attachment {
        private String name;
        private InputStreamSource inputStreamSource;

        public Attachment(String name, InputStreamSource inputStreamSource) {
            this.name = name;
            this.inputStreamSource = inputStreamSource;
        }
    }


}