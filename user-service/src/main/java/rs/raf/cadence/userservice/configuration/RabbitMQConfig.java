package rs.raf.cadence.userservice.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue passwordActivationQueue() {
        return new Queue("password-activation", false);
    }

    @Bean
    public Queue passwordChangeQueue() {
        return new Queue("password-change", false);
    }

    @Bean
    public Queue passwordForgotQueue() {
        return new Queue("password-forgot", false);
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
