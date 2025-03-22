package rs.raf.cadence.notificationservice.listeners;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import rs.raf.cadence.notificationservice.data.entities.EmailNotification;
import rs.raf.cadence.notificationservice.services.EmailService;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class RabbitMQListener {
    private final EmailService emailService;

    @RabbitListener(queues = "account-verification")
    public void processAccountVerificationEmail(Map<String, Object> message) {
        EmailNotification notification = new EmailNotification(
                (String) message.get("to"),
                (String) message.get("subject"),
                (String) message.get("content")
        );

        emailService.sendEmail(notification);
    }
}
