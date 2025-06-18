package rs.raf.cadence.notificationservice.listeners;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import rs.raf.cadence.notificationservice.data.entities.EmailNotification;
import rs.raf.cadence.notificationservice.services.EmailService;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class RabbitMQListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQListener.class);

    private final EmailService emailService;

    @RabbitListener(queues = "account-verification")
    public void processAccountVerificationEmail(Map<String, Object> message) {
        LOGGER.info("Processing account verification email for: {}", message.get("to"));

        try {
            EmailNotification notification = new EmailNotification(
                    (String) message.get("to"),
                    (String) message.get("subject"),
                    (String) message.get("content"),
                    (String) message.get("contentType")
            );

            emailService.sendEmail(notification);
            LOGGER.info("Account verification email sent successfully to: {}", message.get("to"));
        } catch (Exception e) {
            LOGGER.error("Failed to send account verification email to: {}", message.get("to"), e);
        }
    }

    @RabbitListener(queues = "email-change")
    public void processEmailChangeEmail(Map<String, Object> message) {
        LOGGER.info("Processing email change verification email for: {}", message.get("to"));

        try {
            EmailNotification notification = new EmailNotification(
                    (String) message.get("to"),
                    (String) message.get("subject"),
                    (String) message.get("content"),
                    (String) message.get("contentType")
            );

            emailService.sendEmail(notification);
            LOGGER.info("Email change verification email sent successfully to: {}", message.get("to"));
        } catch (Exception e) {
            LOGGER.error("Failed to send email change verification email to: {}", message.get("to"), e);
        }
    }

    @RabbitListener(queues = "password-change")
    public void processPasswordChangeEmail(Map<String, Object> message) {
        LOGGER.info("Processing password change notification email for: {}", message.get("to"));

        try {
            EmailNotification notification = new EmailNotification(
                    (String) message.get("to"),
                    (String) message.get("subject"),
                    (String) message.get("content"),
                    (String) message.get("contentType")
            );

            emailService.sendEmail(notification);
            LOGGER.info("Password change notification email sent successfully to: {}", message.get("to"));
        } catch (Exception e) {
            LOGGER.error("Failed to send password change notification email to: {}", message.get("to"), e);
        }
    }
}
