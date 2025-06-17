package sptech.school.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.br.CPF;
import sptech.school.domain.enumerated.Subject;

import java.time.LocalDate;
import java.util.List;

public record TeacherRequestUpdateDTO(
        String name
        , @Email String email
        , @CPF String cpf
        , Subject subject
        , String cellphoneNumber
        , @Past LocalDate dateBirth
        , String resumeTeacher
        , String yearsExperience
        , String academicFormation
        , Double hourlyRate
) {}