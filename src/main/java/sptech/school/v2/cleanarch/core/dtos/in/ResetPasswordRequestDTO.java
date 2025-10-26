package sptech.school.v2.cleanarch.core.dtos.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequestDTO(

        @Email
        @NotBlank
        String email,

        @NotBlank
        @Size(min = 6)
        String newPassword

) {}
