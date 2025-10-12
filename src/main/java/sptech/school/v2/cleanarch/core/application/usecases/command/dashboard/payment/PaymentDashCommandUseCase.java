package sptech.school.v2.cleanarch.core.application.usecases.command.dashboard.payment;

import org.springframework.stereotype.Service;
import sptech.school.domain.enumerated.PaymentStatus;
import sptech.school.v2.cleanarch.core.application.gateways.dashboard.payment.PaymentDashCommandGateway;

@Service
public class PaymentDashCommandUseCase {
    private final PaymentDashCommandGateway gateway;

    public PaymentDashCommandUseCase(PaymentDashCommandGateway gateway) {
        this.gateway = gateway;
    }

    public PaymentStatus togglePaymentStatus(Integer appointmentId) {
        return gateway.togglePaymentStatus(appointmentId);
    }
}

