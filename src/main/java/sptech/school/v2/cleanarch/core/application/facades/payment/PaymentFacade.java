package sptech.school.v2.cleanarch.core.application.facades.payment;

import org.springframework.stereotype.Service;
import com.mercadopago.resources.payment.Payment;
import sptech.school.v2.cleanarch.core.application.usecases.command.payment.PaymentCommandUseCase;
import sptech.school.v2.cleanarch.core.dtos.in.payment.PaymentRequestDTO;
import sptech.school.v2.cleanarch.core.dtos.internal.payment.payment.PreferenceDTO;


@Service
public class PaymentFacade implements PaymentFacadeContract {

    private final PaymentCommandUseCase commandUseCase;

    public PaymentFacade(PaymentCommandUseCase commandUseCase) {
        this.commandUseCase = commandUseCase;
    }

    @Override
    public Payment processPayment(PaymentRequestDTO request) throws Exception {
        return commandUseCase.processPayment(request);
    }

    @Override
    public String createPreference(PreferenceDTO dto) {
        return commandUseCase.createPreference(dto);
    }
}