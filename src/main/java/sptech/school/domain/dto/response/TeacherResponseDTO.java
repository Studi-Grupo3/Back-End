package sptech.school.domain.dto.response;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;
import sptech.school.domain.enumerated.Subject;

import java.time.LocalDate;
import java.util.List;

public record TeacherResponseDTO(
        @NotNull Integer id
        , @NotBlank String name
        , @Email @NotBlank String email
        , @CPF String cpf
        , @ElementCollection @Enumerated(EnumType.STRING) List<Subject> subject
        , String cellphoneNumber
        , LocalDate dateBirth)
{}