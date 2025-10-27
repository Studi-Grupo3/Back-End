package sptech.school.v2.cleanarch.core.dtos.payments;

import jakarta.validation.constraints.NotBlank;

public record AddressDTO(
       @NotBlank String streetName,
       @NotBlank String streetNumber,
       @NotBlank String zipCode
) {
}