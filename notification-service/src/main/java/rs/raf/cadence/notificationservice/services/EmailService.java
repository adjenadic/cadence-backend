package rs.raf.cadence.notificationservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import rs.raf.cadence.notificationservice.data.entities.EmailNotification;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendEmail(EmailNotification notification) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(notification.getTo());
        message.setSubject(notification.getSubject());
        message.setText(notification.getContent());

        mailSender.send(message);
    }
}
