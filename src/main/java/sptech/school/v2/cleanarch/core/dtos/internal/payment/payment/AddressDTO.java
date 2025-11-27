package sptech.school.v2.cleanarch.core.dtos.internal.payment.payment;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AddressDTO(
       @NotBlank String streetName,
       @NotBlank String streetNumber,
       @NotBlank String zipCode
) {
}