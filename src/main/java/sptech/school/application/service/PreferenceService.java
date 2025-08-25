package sptech.school.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sptech.school.application.config.PaymentConfig;

import java.util.Map;

@Service
public class PreferenceService {
    private static final String MERCADO_PAGO_URL = "https://api.mercadopago.com/checkout/preferences";
    private final WebClient webClient;
    private final PaymentConfig paymentConfig;
    private final ObjectMapper objectMapper;

    public PreferenceService(WebClient.Builder webClientBuilder, PaymentConfig paymentConfig, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.baseUrl(MERCADO_PAGO_URL).build();
        this.paymentConfig = paymentConfig;
        this.objectMapper = objectMapper;
    }

    public String createPreference(double amount, String payerEmail) {
        Map<String, Object> payload = Map.of(
                "items", new Object[]{
                        Map.of(
                                "title", "Produto Exemplo",
                                "quantity", 1,
                                "unit_price", amount,
                                "currency_id", "BRL"
                        )
                },
                "payer", Map.of("email", payerEmail)
        );

        String responseJson = null;

        try {
            responseJson = webClient.post()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + paymentConfig.getAccessToken())
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(payload)
                    .retrieve()
                    .onStatus(status -> status.isError(), response -> response.bodyToMono(String.class)
                            .flatMap(errorBody -> {
                                System.err.println("Erro do Mercado Pago: " + errorBody);
                                return Mono.error(new RuntimeException("Erro na API do Mercado Pago: " + errorBody));
                            }))
                    .bodyToMono(String.class)
                    .block();

            System.out.println("Resposta do Mercado Pago: " + responseJson);

            if (responseJson == null || responseJson.isEmpty()) {
                throw new RuntimeException("Resposta vazia do Mercado Pago");
            }

            JsonNode jsonNode = objectMapper.readTree(responseJson);

            if (jsonNode.has("id")) {
                return jsonNode.get("id").asText();
            } else {
                throw new RuntimeException("Resposta inválida: 'id' não encontrado na resposta do Mercado Pago.");
            }

        } catch (Exception e) {
            System.err.println("Erro ao processar resposta do Mercado Pago: " + e.getMessage());
            throw new RuntimeException("Erro ao criar preferência de pagamento: " + e.getMessage(), e);
        }
    }
}

