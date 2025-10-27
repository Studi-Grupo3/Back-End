package sptech.school.v2.cleanarch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfig {

    @Value("${mercadopago.access.token}")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }
}
