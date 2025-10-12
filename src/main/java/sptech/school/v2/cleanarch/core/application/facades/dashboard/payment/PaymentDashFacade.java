package sptech.school.v2.cleanarch.core.application.facades.dashboard.payment;

import org.springframework.stereotype.Service;
import sptech.school.v2.cleanarch.core.application.usecases.query.dashboard.payment.PaymentDashQueryUseCase;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.payment.PaymentDashResponseDTO;

@Service
public class PaymentDashFacade implements PaymentDashFacadeContract {

    private final PaymentDashQueryUseCase paymentDashQueryUseCase;

    public PaymentDashFacade(PaymentDashQueryUseCase paymentDashQueryUseCase) {
        this.paymentDashQueryUseCase = paymentDashQueryUseCase;
    }

    @Override
    public PaymentDashResponseDTO getPaymentDashData() {
        return paymentDashQueryUseCase.getAllDashboardData();
    }
}

