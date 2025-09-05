package sptech.school.v2.cleanarch.core.dtos.out;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;
import sptech.school.domain.enumerated.Subject;

import java.time.LocalDate;
import java.util.List;

public record TeacherResponseDTO(
        @NotNull Integer id
        , @NotBlank String name
        , @Email @NotBlank String email
        , @CPF String cpf
        , List<Subject> subjects
        , String cellphoneNumber
        , LocalDate dateBirth
        , String resumeTeacher
        , String yearsExperience
        , String academicFormation
        , String hourlyRate
)
{}