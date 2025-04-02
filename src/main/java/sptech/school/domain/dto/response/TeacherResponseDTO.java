package sptech.school.domain.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;
import sptech.school.domain.enumerated.Discipline;

public record TeacherResponseDTO(
        @NotBlank String name
        , @Email @NotBlank String email
        , @CPF @NotBlank String cpf
        , @NotBlank Discipline discipline) {
}
