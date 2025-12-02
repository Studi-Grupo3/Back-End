package sptech.school.v2.cleanarch.core.application.gateways.dashboard.payment;

import sptech.school.v2.cleanarch.domain.enumerated.PaymentStatus;

public interface PaymentDashCommandGateway {
    PaymentStatus togglePaymentStatus(Integer appointmentId, int month, int year);
    Object togglePaymentsStatus(Integer teacherId, int month, int year);
}
