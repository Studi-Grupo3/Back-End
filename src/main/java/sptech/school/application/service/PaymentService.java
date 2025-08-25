package sptech.school.application.service;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.resources.payment.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sptech.school.domain.dto.payments.PaymentRequestDTO;

import java.time.OffsetDateTime;

@Service
public class PaymentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    private final String accessToken;

    public PaymentService(@Value("${mercadopago.access.token}") String accessToken) {
        this.accessToken = accessToken;

        if (accessToken == null || accessToken.isEmpty()) {
            logger.error("❌ ERRO: O Access Token do Mercado Pago não foi carregado!");
        } else {
            logger.info("✅ Mercado Pago Access Token carregado: {}", accessToken);
        }

        MercadoPagoConfig.setAccessToken(accessToken);
    }

    public Payment processPayment(PaymentRequestDTO request) throws Exception {
        try {
            MercadoPagoConfig.setAccessToken(accessToken);

            logger.info("📤 Enviando pagamento para o Mercado Pago...");
            logger.info("💰 Valor: {}", request.transactionAmount());
            logger.info("💳 Método de pagamento: {}", request.paymentMethodId());
            logger.info("📦 Descrição: {}", request.description());
            logger.info("🔢 Parcelas: {}", request.installments());
            logger.info("🧑 Payer Email: {}", request.payer().email());
            logger.info("👤 Payer Identification Type: {}", request.payer().identification().type());
            logger.info("👤 Payer Identification Number: {}", request.payer().identification().number());

            PaymentClient client = new PaymentClient();

            PaymentPayerRequest payer = PaymentPayerRequest.builder()
                    .email(request.payer().email())
                    .firstName(request.payer().firstName())
                    .identification(IdentificationRequest.builder()
                            .type(request.payer().identification().type())
                            .number(request.payer().identification().number())
                            .build())
                    .build();

            PaymentCreateRequest.PaymentCreateRequestBuilder paymentRequestBuilder = PaymentCreateRequest.builder()
                    .transactionAmount(request.transactionAmount())
                    .description(request.description())
                    .paymentMethodId(request.paymentMethodId())
                    .payer(payer)
                    .installments(request.installments());

            if ("pix".equalsIgnoreCase(request.paymentMethodId()) ||
                    "bolbradesco".equalsIgnoreCase(request.paymentMethodId())) {
                paymentRequestBuilder.dateOfExpiration(OffsetDateTime.now().plusDays(2));
            } else if (!"bolbradesco".equalsIgnoreCase(request.paymentMethodId())) {
                paymentRequestBuilder.token(request.token());
            }

            PaymentCreateRequest paymentCreateRequest = paymentRequestBuilder.build();
            Payment payment = client.create(paymentCreateRequest);

            logger.info("✅ Pagamento criado: ID={}, Status={}, Tipo={}",
                    payment.getId(), payment.getStatus(), request.paymentMethodId());

            return payment;

        } catch (Exception e) {
            logger.error("❌ Erro ao processar pagamento: {}", e.getMessage(), e);
            throw new Exception("Erro ao processar pagamento: " + e.getMessage());
        }
    }
}