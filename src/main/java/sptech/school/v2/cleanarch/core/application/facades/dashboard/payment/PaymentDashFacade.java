package sptech.school.v2.cleanarch.core.application.facades.dashboard.payment;

import org.springframework.stereotype.Service;
import sptech.school.v2.cleanarch.core.application.usecases.query.dashboard.payment.PaymentDashQueryUseCase;
import sptech.school.v2.cleanarch.core.application.usecases.command.dashboard.payment.PaymentDashCommandUseCase;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.payment.PaymentDashResponseDTO;
import sptech.school.v2.cleanarch.domain.enumerated.PaymentStatus;

@Service
public class PaymentDashFacade implements PaymentDashFacadeContract {

    private final PaymentDashQueryUseCase paymentDashQueryUseCase;
    private final PaymentDashCommandUseCase paymentDashCommandUseCase;

    public PaymentDashFacade(PaymentDashQueryUseCase paymentDashQueryUseCase,
                             PaymentDashCommandUseCase paymentDashCommandUseCase) {
        this.paymentDashQueryUseCase = paymentDashQueryUseCase;
        this.paymentDashCommandUseCase = paymentDashCommandUseCase;
    }

    @Override
    public PaymentDashResponseDTO getPaymentDashData(int month, int year) {
        return paymentDashQueryUseCase.getAllDashboardData(month, year);
    }

    @Override
    public PaymentStatus togglePaymentStatus(Integer appointmentId, int month, int year) {
        return paymentDashCommandUseCase.togglePaymentStatus(appointmentId, month, year);
    }

    @Override
    public Object togglePaymentsStatus(Integer teacherId, int month, int year) {
        return paymentDashCommandUseCase.togglePaymentsStatus(teacherId, month, year);
    }
}
