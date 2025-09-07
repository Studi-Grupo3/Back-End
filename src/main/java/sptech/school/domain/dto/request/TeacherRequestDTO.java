package sptech.school.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;
import sptech.school.v2.cleanarch.domain.enumerated.Subject;

public record TeacherRequestDTO(
        @NotBlank String name
        , @Email @NotBlank String email
        , @CPF String cpf
        , @NotBlank String password
        , Subject subject
) {
}
