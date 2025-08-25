package sptech.school.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record LoginRequestDTO(
          @Email @NotBlank String email
        , @NotBlank String password
) {}