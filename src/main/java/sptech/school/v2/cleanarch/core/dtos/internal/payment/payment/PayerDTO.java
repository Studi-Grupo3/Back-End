package sptech.school.v2.cleanarch.core.dtos.internal.payment.payment;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PayerDTO(
        @NotBlank @Email String email,
        @NotBlank String firstName,
        @Valid @NotNull IdentificationDTO identification,
        @NotNull
        @Valid AddressDTO address
) {
}