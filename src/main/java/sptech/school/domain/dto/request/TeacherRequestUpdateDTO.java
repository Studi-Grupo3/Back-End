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
        , String password
        , Subject subject
        , @Pattern(regexp = "\\d{8,12}", message = "The cellphone number must contain 8 - 12 digits.")
          String cellphoneNumber
        , @Past LocalDate dateBirth
        , String schoolName
) { }