package sptech.school.domain.dto.payments;

public record PayerDTO(
        String email,
        String firstName,
        IdentificationDTO identification,
        AddressDTO address
) {
}