package sptech.school.v2.cleanarch.core.dtos.in.payment;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import sptech.school.v2.cleanarch.core.dtos.internal.payment.payment.PayerDTO;

import java.math.BigDecimal;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PaymentRequestDTO(
        @NotNull @Positive BigDecimal transactionAmount,
        @NotBlank String token,
        @NotBlank String description,
        @NotNull @Positive Integer installments,
        @NotNull String paymentMethodId,
        @Valid PayerDTO payer
) {
}