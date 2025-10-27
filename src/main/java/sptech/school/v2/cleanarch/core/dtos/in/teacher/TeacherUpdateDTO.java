package sptech.school.v2.cleanarch.core.dtos.in.teacher;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.br.CPF;
import sptech.school.v2.cleanarch.domain.enumerated.Subject;

import java.time.LocalDate;
import java.util.List;

public record TeacherUpdateDTO(
        String name
        , @Email String email
        , @CPF String cpf
        , List<Subject> subjects
        , String cellphoneNumber
        , @Past LocalDate dateBirth
        , String resumeTeacher
        , String yearsExperience
        , String academicFormation
        , Double hourlyRate
) {}