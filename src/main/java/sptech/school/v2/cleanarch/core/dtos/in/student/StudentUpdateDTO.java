package sptech.school.v2.cleanarch.core.dtos.in.student;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.br.CPF;
import sptech.school.domain.entity.Responsible;

import java.time.LocalDate;

public record StudentUpdateDTO(
          String name
        , @Email String email
        , @CPF String cpf
        , String cellphoneNumber
        , @Past LocalDate dateBirth
        , String schoolGrade
        , String schoolName
        , Responsible responsible
) {}
