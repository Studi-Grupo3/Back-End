package sptech.school.v2.cleanarch.core.dtos.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record StudentRegisterDTO(
        @NotBlank String name
        , @Email @NotBlank String email
        , @CPF String cpf
        , @NotBlank String password
) {
}