package sptech.school.v2.cleanarch.core.dtos.internal.payment.payment;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record PreferenceDTO(
        double amount,
        @NotBlank @Email String payer_email) {
}
