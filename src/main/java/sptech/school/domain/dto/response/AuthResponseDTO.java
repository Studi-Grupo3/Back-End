package sptech.school.domain.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serial;
import java.io.Serializable;

public record AuthResponseDTO(
        @NotBlank String username
        , @NotBlank @CPF String cpf
        , @NotBlank @Email String email
        , @NotBlank String token) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}
