package sptech.school.v2.cleanarch.core.application.usecases.command.queue;

import org.springframework.stereotype.Service;
import sptech.school.v2.cleanarch.core.application.gateways.queue.PaymentCommandGateway;
import sptech.school.v2.cleanarch.core.dtos.in.queue.PaymentCreatedEvent;


import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class PaymentCommandUseCase {

    private final PaymentCommandGateway gateway;

    public PaymentCommandUseCase(PaymentCommandGateway gateway) {
        this.gateway = gateway;
    }

    public void publishPaymentCreated(String paymentId, String orderId, Double amount, String status, String payerEmail) {
        PaymentCreatedEvent ev = new PaymentCreatedEvent();
        ev.setMessageId(UUID.randomUUID().toString());
        ev.setPaymentId(paymentId);
        ev.setOrderId(orderId);
        ev.setAmount(amount);
        ev.setStatus(status);
        ev.setPayerEmail(payerEmail);
        ev.setHappenedAt(OffsetDateTime.now());
        gateway.sendPaymentCreated(ev);
    }
}
