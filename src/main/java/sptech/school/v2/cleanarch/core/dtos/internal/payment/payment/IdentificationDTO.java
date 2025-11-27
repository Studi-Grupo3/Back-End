package sptech.school.v2.cleanarch.core.dtos.internal.payment.payment;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record IdentificationDTO(
        @NotBlank String type,
        @NotBlank String number
) {
}