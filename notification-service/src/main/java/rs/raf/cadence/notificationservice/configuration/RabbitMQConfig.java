package rs.raf.cadence.notificationservice.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue accountVerificationQueue() {
        return new Queue("account-verification", false);
    }

    @Bean
    public Queue emailChangeQueue() {
        return new Queue("email-change", false);
    }

    @Bean
    public Queue passwordChangeQueue() {
        return new Queue("password-change", false);
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
