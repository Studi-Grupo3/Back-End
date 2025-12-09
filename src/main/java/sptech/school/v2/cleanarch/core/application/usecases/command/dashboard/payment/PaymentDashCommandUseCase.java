package sptech.school.v2.cleanarch.core.application.usecases.command.dashboard.payment;

import org.springframework.stereotype.Service;
import sptech.school.v2.cleanarch.core.application.gateways.dashboard.payment.PaymentDashCommandGateway;
import sptech.school.v2.cleanarch.domain.enumerated.PaymentStatus;

@Service
public class PaymentDashCommandUseCase {
    private final PaymentDashCommandGateway gateway;

    public PaymentDashCommandUseCase(PaymentDashCommandGateway gateway) {
        this.gateway = gateway;
    }

    public PaymentStatus togglePaymentStatus(Integer appointmentId, int month, int year) {
        return gateway.togglePaymentStatus(appointmentId, month, year);
    }

    public Object togglePaymentsStatus(Integer teacherId, int month, int year) {
        return gateway.togglePaymentsStatus(teacherId, month, year);
    }
}
