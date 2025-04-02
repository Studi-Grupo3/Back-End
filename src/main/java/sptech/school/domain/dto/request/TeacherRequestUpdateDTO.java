package sptech.school.domain.dto.request;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;
import sptech.school.domain.enumerated.Discipline;

public record TeacherRequestUpdateDTO(
          String name
        , @Email String email
        , @CPF String cpf
        , String password
        , Discipline discipline
) { }