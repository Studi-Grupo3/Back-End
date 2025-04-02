package sptech.school.domain.dto.payments;

import java.math.BigDecimal;

public record PaymentRequestDTO(
        BigDecimal transactionAmount,
        String token,
        String description,
        Integer installments,
        String paymentMethodId,
        PayerDTO payer
) {
}