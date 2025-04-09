package sptech.school.domain.dto.payments;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PayerDTO(
        @NotBlank @Email String email,
        @NotBlank String firstName,
        @Valid @NotNull IdentificationDTO identification,
        @NotNull
        @Valid AddressDTO address
) {
}