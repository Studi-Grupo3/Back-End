package sptech.school.v2.cleanarch.infra.persistence.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import sptech.school.v2.cleanarch.config.RabbitConfig;
import sptech.school.v2.cleanarch.core.application.gateways.queue.PaymentCommandGateway;
import sptech.school.v2.cleanarch.core.application.gateways.queue.PaymentRequestCommandGateway;
import sptech.school.v2.cleanarch.core.dtos.in.queue.PaymentCreatedEvent;
import sptech.school.v2.cleanarch.core.dtos.in.queue.PaymentRequestedEvent;

@Component
public class RabbitPaymentGateway implements PaymentCommandGateway, PaymentRequestCommandGateway {

    private final RabbitTemplate rabbitTemplate;

    public RabbitPaymentGateway(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendPaymentCreated(PaymentCreatedEvent event) {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_PAYMENT, event);
    }

    @Override
    public void sendPaymentRequested(PaymentRequestedEvent event) {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_PAYMENT_REQUEST, event);
    }
}
