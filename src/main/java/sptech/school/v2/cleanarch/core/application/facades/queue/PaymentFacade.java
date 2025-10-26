package sptech.school.v2.cleanarch.core.application.facades.queue;

import org.springframework.stereotype.Service;
import sptech.school.domain.dto.payments.PaymentRequestDTO;
import sptech.school.v2.cleanarch.core.application.usecases.command.queue.PaymentRequestCommandUseCase;

@Service
public class PaymentFacade implements PaymentFacadeContract {

    private final PaymentRequestCommandUseCase requestCommandUseCase;

    public PaymentFacade(PaymentRequestCommandUseCase requestCommandUseCase) {
        this.requestCommandUseCase = requestCommandUseCase;
    }

    @Override
    public String requestAsyncPayment(PaymentRequestDTO dto) {
        return requestCommandUseCase.publishPaymentRequested(dto);
    }
}

