package sptech.school.v2.cleanarch.core.application.usecases.command.queue;

import org.springframework.stereotype.Service;
import sptech.school.domain.dto.payments.PaymentRequestDTO;
import sptech.school.v2.cleanarch.core.application.gateways.queue.PaymentRequestCommandGateway;
import sptech.school.v2.cleanarch.core.dtos.in.queue.PaymentRequestedEvent;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class PaymentRequestCommandUseCase {

    private final PaymentRequestCommandGateway gateway;

    public PaymentRequestCommandUseCase(PaymentRequestCommandGateway gateway) {
        this.gateway = gateway;
    }

    public String publishPaymentRequested(PaymentRequestDTO dto) {
        PaymentRequestedEvent ev = new PaymentRequestedEvent();
        ev.setMessageId(UUID.randomUUID().toString());
        ev.setTransactionAmount(dto.transactionAmount());
        ev.setPaymentMethodId(dto.paymentMethodId());
        ev.setDescription(dto.description());
        ev.setInstallments(dto.installments());
        ev.setPayerEmail(dto.payer().email());
        ev.setPayerFirstName(dto.payer().firstName());
        ev.setPayerIdentificationType(dto.payer().identification().type());
        ev.setPayerIdentificationNumber(dto.payer().identification().number());
        ev.setCardToken(dto.token());
        ev.setHappenedAt(OffsetDateTime.now());
        gateway.sendPaymentRequested(ev);
        return ev.getMessageId();
    }
}
