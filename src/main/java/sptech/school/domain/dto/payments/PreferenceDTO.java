package sptech.school.domain.dto.payments;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record PreferenceDTO(
        @Positive double amount,
        @NotBlank @Email String payer_email) {
}
