package sptech.school.domain.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record StudentResponseDTO(
        @NotBlank String name
        , @Email @NotBlank String email
) { }