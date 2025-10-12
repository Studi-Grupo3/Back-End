package sptech.school.v2.cleanarch.core.application.facades.queue;

import sptech.school.domain.dto.payments.PaymentRequestDTO;

public interface PaymentFacadeContract {
    String requestAsyncPayment(PaymentRequestDTO dto);
}

