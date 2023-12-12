package com.weekytripstravelcrm.email;

import com.weekytripstravelcrm.repository.EmailStatusRepository;
import com.weekytripstravelcrm.util.KeyGenerator;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender sender;
    @Autowired
    private Configuration freemarkerConfig;
    @Autowired
    private EmailStatusRepository emailStatusRepository;

    public MailResponse sendEmail(MailRequest request, Map<String, Object> model) {
        MailResponse response = new MailResponse();
        MimeMessage message = sender.createMimeMessage();
        EmailStatus emailStatus = new EmailStatus();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            Template template = freemarkerConfig.getTemplate(request.getTemplateName());
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            /**
             * email id (auto generated ),Store to_email address CC email address, subject, status - pending
             * create email table which will track the status of email
             */

            String prefix = "ES";
            Long maxEmailStatusId = emailStatusRepository.findMaxEmailStatusIdAsLong();
            Long emailStatusStartingValue = 1000L;
            emailStatus.setEmailStatusId(KeyGenerator.generateId(prefix,maxEmailStatusId, EmailStatus.class,emailStatusStartingValue));
            emailStatus.setFromEmailAddress(request.getFrom());
            emailStatus.setToEmailAddress(request.getTo());
            emailStatus.setSubject(request.getSubject());
            emailStatus.setStatus("Pending");

            helper.setFrom(request.getFrom());
            helper.setTo(request.getTo());
            helper.setText(html, true);
            helper.setSubject(request.getSubject());
            emailStatusRepository.save(emailStatus);

            sender.send(message);
            /**
             * after successful send, status will be true ( completed)
             */
            EmailStatus emailStatus1 = emailStatusRepository.findByEmailStatusId(emailStatus.getEmailStatusId());
            emailStatus1.setStatus("Completed");
            emailStatusRepository.save(emailStatus1);

            response.setMessage("Mail sent to: " + request.getTo());
            response.setStatus(true);
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
            response.setMessage("Mail Sending Failure: " + e.getMessage());
            response.setStatus(false);
        }

        return response;
    }
}
