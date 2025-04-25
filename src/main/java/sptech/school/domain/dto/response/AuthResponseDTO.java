package sptech.school.domain.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;
import sptech.school.domain.enumerated.Role;

import java.io.Serial;
import java.io.Serializable;

public record AuthResponseDTO(
        Integer id,
        @NotBlank String username
        , @NotBlank @CPF String cpf
        , @NotBlank @Email String email
        , @NotBlank String token
        , @NotBlank Role role
) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}
