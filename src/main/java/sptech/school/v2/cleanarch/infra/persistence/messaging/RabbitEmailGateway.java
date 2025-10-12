package sptech.school.v2.cleanarch.infra.persistence.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import sptech.school.application.config.RabbitConfig;
import sptech.school.v2.cleanarch.core.application.gateways.queue.EmailCommandGateway;
import sptech.school.v2.cleanarch.core.dtos.in.queue.ContactRequestedEvent;
import sptech.school.v2.cleanarch.core.dtos.in.queue.PasswordResetRequestedEvent;

@Component
public class RabbitEmailGateway implements EmailCommandGateway {

    private final RabbitTemplate rabbitTemplate;

    public RabbitEmailGateway(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendContactEvent(ContactRequestedEvent event) {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_CONTACT, event);
    }

    @Override
    public void sendPasswordResetEvent(PasswordResetRequestedEvent event) {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_PASSWORD, event);
    }
}
