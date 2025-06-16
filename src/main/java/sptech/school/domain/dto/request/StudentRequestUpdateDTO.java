package sptech.school.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;
import sptech.school.domain.entity.Responsible;

import java.time.LocalDate;

public record StudentRequestUpdateDTO(
          String name
        , @Email String email
        , @CPF String cpf
        ,  String cellphoneNumber
        , @Past LocalDate dateBirth
        ,  String responsibleCellphone
        , String schoolGrade
        , String schoolName
        , Responsible responsible
        , String yearsExperience
        , String academicFormation
) {}
