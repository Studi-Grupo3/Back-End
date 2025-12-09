package sptech.school.v2.cleanarch.core.application.facades.dashboard.payment;

import sptech.school.v2.cleanarch.core.dtos.out.dashboard.payment.PaymentDashResponseDTO;
import sptech.school.v2.cleanarch.domain.enumerated.PaymentStatus;

public interface PaymentDashFacadeContract {

    PaymentDashResponseDTO getPaymentDashData(int month, int year);
    PaymentStatus togglePaymentStatus(Integer appointmentId, int month, int year);
    Object togglePaymentsStatus(Integer teacherId, int month, int year);
}
