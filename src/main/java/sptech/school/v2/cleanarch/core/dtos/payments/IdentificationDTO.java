package sptech.school.v2.cleanarch.core.dtos.payments;

import jakarta.validation.constraints.NotBlank;

public record IdentificationDTO(
        @NotBlank String type,
        @NotBlank String number
) {
}