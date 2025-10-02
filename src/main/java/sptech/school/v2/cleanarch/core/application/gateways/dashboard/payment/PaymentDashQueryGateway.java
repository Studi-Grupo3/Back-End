package sptech.school.v2.cleanarch.core.application.gateways.dashboard.payment;

import sptech.school.v2.cleanarch.core.dtos.out.dashboard.payment.PaymentDashResponseDTO;

import java.time.LocalDateTime;

public interface PaymentDashQueryGateway {
    PaymentDashResponseDTO getPaymentDashData(LocalDateTime start, LocalDateTime end);
}
