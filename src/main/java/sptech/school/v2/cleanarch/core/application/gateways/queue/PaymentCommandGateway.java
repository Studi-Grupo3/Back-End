package sptech.school.v2.cleanarch.core.application.gateways.queue;


import sptech.school.v2.cleanarch.core.dtos.in.queue.PaymentCreatedEvent;

public interface PaymentCommandGateway {
    void sendPaymentCreated(PaymentCreatedEvent event);
}
