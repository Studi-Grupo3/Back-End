package sptech.school.v2.cleanarch.core.teacher.dtos.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;
import sptech.school.domain.enumerated.Subject;

import java.util.List;

public record TeacherRegisterDTO(
        @NotBlank String name
        , @Email @NotBlank String email
        , @CPF String cpf
        , @NotBlank String password
        , List<Subject> subjects) {
}