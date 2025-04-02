package sptech.school.domain.dto.payments;

public record AddressDTO(
        String streetName,
        String streetNumber,
        String zipCode
) {
}