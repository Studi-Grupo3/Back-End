package sptech.school.v2.cleanarch.core.teacher.dtos.out;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;
import sptech.school.domain.enumerated.Subject;

import java.time.LocalDate;

public record TeacherResponseDTO(
        @NotNull Integer id
        , @NotBlank String name
        , @Email @NotBlank String email
        , @CPF String cpf
        , Subject subject
        , String cellphoneNumber
        , LocalDate dateBirth
        , String resumeTeacher
        , String yearsExperience
        , String academicFormation
        , String hourlyRate
)
{}