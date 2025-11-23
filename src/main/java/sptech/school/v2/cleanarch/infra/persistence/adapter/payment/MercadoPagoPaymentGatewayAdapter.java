package sptech.school.v2.cleanarch.infra.persistence.adapter.payment;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.resources.payment.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import sptech.school.v2.cleanarch.core.application.gateways.payment.PaymentGateway;
import sptech.school.v2.cleanarch.core.dtos.in.payment.PaymentRequestDTO;

import java.time.OffsetDateTime;

@Component
public class MercadoPagoPaymentGatewayAdapter implements PaymentGateway {

    private static final Logger logger = LoggerFactory.getLogger(MercadoPagoPaymentGatewayAdapter.class);
    private final String accessToken;

    public MercadoPagoPaymentGatewayAdapter() {
        this.accessToken = System.getenv("MERCADOPAGO_ACCESS_TOKEN");
        if (this.accessToken == null || this.accessToken.isEmpty()) {
            logger.error("Access Token do Mercado Pago não carregado!");
        } else {
            logger.info("Access Token do Mercado Pago carregado com sucesso.");
            MercadoPagoConfig.setAccessToken(this.accessToken);
        }
    }

    @Override
    public Payment processPayment(PaymentRequestDTO request) throws Exception {
        try {
            MercadoPagoConfig.setAccessToken(accessToken);
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
            throw new Exception("Erro ao processar pagamento: " + e.getMessage(), e);
        }
    }
}