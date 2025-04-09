package sptech.school.domain.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;
import sptech.school.domain.enumerated.Discipline;

public record TeacherRequestDTO(
        @NotBlank String name
        , @Email @NotBlank String email
        , @CPF @NotBlank String cpf
        , @NotBlank String password
        , @NotNull @Enumerated(EnumType.STRING) Discipline discipline
) {
}
