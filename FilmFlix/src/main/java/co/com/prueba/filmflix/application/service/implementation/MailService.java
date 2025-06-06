package co.com.prueba.filmflix.application.service.implementation;

import co.com.prueba.filmflix.application.service.IMailService;
import co.com.prueba.filmflix.domain.dto.mail.MailBody;
import co.com.prueba.filmflix.domain.entities.Ticket;
import co.com.prueba.filmflix.domain.entities.User;
import co.com.prueba.filmflix.utils.exceptions.MailException;
import co.com.prueba.filmflix.utils.validations.MailValidator;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.UnsupportedEncodingException;

@RequiredArgsConstructor
@Service
public class MailService implements IMailService {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine springTemplateEngine;

    @Value("${spring.mail.from-address}")
    private String fromEmail;

    private void sendMail(MailBody mailBody) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(mailBody.getTo());

            try {
                helper.setFrom(new InternetAddress(fromEmail, "Films Management"));
            } catch (UnsupportedEncodingException e) {
                throw new MailException(String.format(MailValidator.EMAIL_ADDRESS_FAILED, fromEmail));
            }

            helper.setSubject(mailBody.getSubject());
            helper.setText(mailBody.getBody(), mailBody.isHtml());

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new MailException(MailValidator.EMAIL_SEND_FAILED);
        }
    }

    public void sendConfirmationEmailRegister(User user) {
        ClassLoaderTemplateResolver secondaryTemplateResolver = new ClassLoaderTemplateResolver();
        secondaryTemplateResolver.setPrefix("templates/");
        Context context = createEmailContextRegister(user);
        String htmlContent = springTemplateEngine.process("registertemplate", context);

        MailBody mailBody = MailBody.builder()
                .to(user.getEmail())
                .subject(MailValidator.EMAIL_REGISTER_TITLE)
                .body(htmlContent)
                .isHtml(true)
                .build();

        sendMail(mailBody);
    }

    public void sendConfirmationEmailTicket(Ticket ticket) {
        ClassLoaderTemplateResolver secondaryTemplateResolver = new ClassLoaderTemplateResolver();
        secondaryTemplateResolver.setPrefix("templates/");
        Context context = createEmailContextTicket(ticket);

        String htmlContent = springTemplateEngine.process("tickettemplate", context);

        MailBody mailBody = MailBody.builder()
                .to(ticket.getUser().getEmail())
                .subject(MailValidator.EMAIL_TICKET_TITLE)
                .body(htmlContent)
                .isHtml(true)
                .build();

        sendMail(mailBody);
    }

    private Context createEmailContextRegister(User user) {
        Context context = new Context();
        context.setVariable("user", user);
        return context;
    }

    private Context createEmailContextTicket(Ticket ticket) {
        Context context = new Context();
        context.setVariable("ticket", ticket);
        context.setVariable("user", ticket.getUser());
        context.setVariable("function", ticket.getFunction());
        context.setVariable("film", ticket.getFilm());
        context.setVariable("referenceCode", generateReferenceCode());

        return context;
    }

    private String generateReferenceCode() {
        return "Recibo - " + ((int) ((Math.random() * 9000) + 1000));
    }
}
