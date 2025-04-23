package sptech.school.domain.dto.payments;

import jakarta.validation.constraints.NotBlank;

public record AddressDTO(
       @NotBlank String streetName,
       @NotBlank String streetNumber,
       @NotBlank String zipCode
) {
}