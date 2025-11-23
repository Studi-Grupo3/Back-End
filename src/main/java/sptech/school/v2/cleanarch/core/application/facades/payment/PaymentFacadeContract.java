package sptech.school.v2.cleanarch.core.application.facades.payment;

import com.mercadopago.resources.payment.Payment;
import sptech.school.v2.cleanarch.core.dtos.in.payment.PaymentRequestDTO;
import sptech.school.v2.cleanarch.core.dtos.internal.payment.payment.PreferenceDTO;


public interface PaymentFacadeContract {

    Payment processPayment(PaymentRequestDTO request) throws Exception;

    String createPreference(PreferenceDTO dto);
}