package sptech.school.application.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "app.exchange";

    public static final String CONTACT_QUEUE = "email.contact.queue";
    public static final String PASSWORD_QUEUE = "email.password.reset.queue";
    public static final String PAYMENT_QUEUE = "payment.created.queue";
    public static final String PAYMENT_REQUEST_QUEUE = "payment.requested.queue";

    public static final String ROUTING_CONTACT = "contact.requested";
    public static final String ROUTING_PASSWORD = "password.reset.requested";
    public static final String ROUTING_PAYMENT = "payment.created";
    public static final String ROUTING_PAYMENT_REQUEST = "payment.requested";

    @Bean
    public DirectExchange appExchange() {
        return new DirectExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue contactQueue() {
        return QueueBuilder.durable(CONTACT_QUEUE).build();
    }

    @Bean
    public Queue passwordQueue() {
        return QueueBuilder.durable(PASSWORD_QUEUE).build();
    }

    @Bean
    public Queue paymentQueue() {
        return QueueBuilder.durable(PAYMENT_QUEUE).build();
    }

    @Bean
    public Queue paymentRequestQueue() {
        return QueueBuilder.durable(PAYMENT_REQUEST_QUEUE).build();
    }

    @Bean
    public Binding bindContact(Queue contactQueue, DirectExchange exchange) {
        return BindingBuilder.bind(contactQueue).to(exchange).with(ROUTING_CONTACT);
    }

    @Bean
    public Binding bindPassword(Queue passwordQueue, DirectExchange exchange) {
        return BindingBuilder.bind(passwordQueue).to(exchange).with(ROUTING_PASSWORD);
    }

    @Bean
    public Binding bindPayment(Queue paymentQueue, DirectExchange exchange) {
        return BindingBuilder.bind(paymentQueue).to(exchange).with(ROUTING_PAYMENT);
    }

    @Bean
    public Binding bindPaymentRequest(Queue paymentRequestQueue, DirectExchange exchange) {
        return BindingBuilder.bind(paymentRequestQueue).to(exchange).with(ROUTING_PAYMENT_REQUEST);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory cf, Jackson2JsonMessageConverter conv) {
        RabbitTemplate template = new RabbitTemplate(cf);
        template.setMessageConverter(conv);
        return template;
    }
}
