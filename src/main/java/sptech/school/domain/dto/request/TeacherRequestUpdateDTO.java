package sptech.school.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;
import sptech.school.domain.enumerated.Subject;

import java.time.LocalDate;

public record TeacherRequestUpdateDTO(
          String name
        , @Email String email
        , @CPF String cpf
        , Subject subject
        , String cellphoneNumber
        , @Past LocalDate dateBirth
        , String resume
) {}