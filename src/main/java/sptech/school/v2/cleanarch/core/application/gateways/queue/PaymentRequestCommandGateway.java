package sptech.school.v2.cleanarch.core.application.gateways.queue;

import sptech.school.v2.cleanarch.core.dtos.in.queue.PaymentRequestedEvent;

public interface PaymentRequestCommandGateway {
    void sendPaymentRequested(PaymentRequestedEvent event);
}

