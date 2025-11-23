package sptech.school.v2.cleanarch.core.application.gateways.payment;

import com.mercadopago.resources.payment.Payment;
import sptech.school.v2.cleanarch.core.dtos.in.payment.PaymentRequestDTO;

public interface PaymentGateway {
    Payment processPayment(PaymentRequestDTO request) throws Exception;
}