package sptech.school.domain.dto.request;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

public record StudentRequestUpdateDTO(
          String name
        , @Email String email
        , @CPF String cpf
        , String password
        , String responsibleCellphone
) {}
