package sptech.school.domain.dto.payments;

import jakarta.validation.constraints.NotBlank;

public record IdentificationDTO(
        @NotBlank String type,
        @NotBlank String number
) {
}