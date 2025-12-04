package sptech.school.v2.cleanarch.core.application.usecases.command.payment;

import org.springframework.stereotype.Service;
import com.mercadopago.resources.payment.Payment;
import sptech.school.v2.cleanarch.core.application.gateways.payment.PaymentGateway;
import sptech.school.v2.cleanarch.core.application.gateways.payment.PreferenceGateway;
import sptech.school.v2.cleanarch.core.dtos.in.payment.PaymentRequestDTO;
import sptech.school.v2.cleanarch.core.dtos.internal.payment.payment.PreferenceDTO;

@Service
public class PaymentCommandUseCase {

    private final PaymentGateway paymentGateway;
    private final PreferenceGateway preferenceGateway;

    public PaymentCommandUseCase(PaymentGateway paymentGateway,
                                 PreferenceGateway preferenceGateway) {
        this.paymentGateway = paymentGateway;
        this.preferenceGateway = preferenceGateway;
    }

    public Payment processPayment(PaymentRequestDTO request) throws Exception {
        return paymentGateway.processPayment(request);
    }

    public String createPreference(PreferenceDTO dto) {
        return preferenceGateway.createPreference(dto.amount(), dto.payer_email());
    }
}