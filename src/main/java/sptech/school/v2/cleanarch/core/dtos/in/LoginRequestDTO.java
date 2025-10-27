package sptech.school.v2.cleanarch.core.dtos.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
          @Email @NotBlank String email
        , @NotBlank String password
) {}