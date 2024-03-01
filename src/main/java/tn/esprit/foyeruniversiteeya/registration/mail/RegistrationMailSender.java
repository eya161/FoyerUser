package tn.esprit.foyeruniversiteeya.registration.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tn.esprit.foyeruniversiteeya.event.RegistrationCompleteEvent;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class RegistrationMailSender {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void sendVerificationEmail(RegistrationCompleteEvent event, String url)
            throws MessagingException, UnsupportedEncodingException {

        String invitationLink = url;
        String subject = "Email Verification";
        String senderName = "User Registration Portal Service";
        Context thymeleafContext = new Context();
        thymeleafContext.setVariable("url", invitationLink);
        String mailContent = templateEngine.process("invitationEmail", thymeleafContext);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, "UTF-8");
        messageHelper.setFrom("prof.dev64@gmail.com", senderName);
        messageHelper.setTo(event.getUser().getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
}
