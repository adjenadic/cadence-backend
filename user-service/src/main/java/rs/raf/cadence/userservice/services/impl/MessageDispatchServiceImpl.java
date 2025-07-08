package rs.raf.cadence.userservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import rs.raf.cadence.userservice.data.entities.User;
import rs.raf.cadence.userservice.repositories.UserRepository;
import rs.raf.cadence.userservice.services.MessageDispatchService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageDispatchServiceImpl implements MessageDispatchService {
    @Value("${app.frontend.url}")
    private String frontendUrl;
    private final RabbitTemplate rabbitTemplate;
    private final TemplateEngine templateEngine;
    private final UserRepository userRepository;

    private void sendEmail(User user, String subject, String templateName, String queueName, boolean includeVerification) {
        Map<String, Object> message = new HashMap<>();
        message.put("to", user.getEmail());
        message.put("username", user.getUsername());
        message.put("subject", subject);

        Context context = new Context();
        context.setVariable("logoUrl", frontendUrl + "/assets/logo/cadence_light_full.png");
        context.setVariable("username", user.getUsername());

        if (includeVerification) {
            String token = UUID.randomUUID().toString();
            user.setVerificationToken(token);
            user.setVerificationTokenExpiry(System.currentTimeMillis() + (24 * 60 * 60 * 1000));
            userRepository.save(user);

            String verificationUrl = frontendUrl + "/verify-email?token=" + token;
            context.setVariable("verificationUrl", verificationUrl);
        }

        String htmlContent = templateEngine.process(templateName, context);
        message.put("content", htmlContent);
        message.put("contentType", "text/html");

        rabbitTemplate.convertAndSend(queueName, message);
    }

    @Override
    public void sendAccountVerificationEmail(User user) {
        sendEmail(user, "Cadence - Verify Your Email", "email-verification", "account-verification", true);
    }

    @Override
    public void sendEmailChangeEmail(User user) {
        sendEmail(user, "Cadence - Verify Your Email Change", "email-change-verification", "email-change", true);
    }

    @Override
    public void sendPasswordChangeEmail(User user) {
        sendEmail(user, "Cadence - Password Changed", "password-change-notification", "password-change", false);
    }
}
