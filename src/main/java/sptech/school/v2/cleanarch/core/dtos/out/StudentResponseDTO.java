package sptech.school.v2.cleanarch.core.dtos.out;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import sptech.school.domain.entity.Responsible;

import java.time.LocalDate;

public record StudentResponseDTO(
        @NotBlank Integer id
        , String name
        , @Email String email
        , @Pattern(regexp = "\\d{8,12}", message = "The cellphone number must contain 8 - 12 digits.")
        String cellphoneNumber
        , @Past LocalDate dateBirth
        , @Pattern(regexp = "\\d{8,12}", message = "The cellphone number must contain 8 - 12 digits.")
        String responsibleCellphone
        , String schoolGrade
        , String schoolName
        , Responsible responsible
        , String yearsExperience
        , String academicFormation
) {
}