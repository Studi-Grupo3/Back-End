package sptech.school.v2.cleanarch.core.dtos.out.teacher;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.web.multipart.MultipartFile;
import sptech.school.v2.cleanarch.domain.enumerated.Subject;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public record TeacherResponseDTO(
        @NotNull Integer id
        , @NotBlank String name
        , @Email @NotBlank String email
        , @CPF String cpf
        , List<Subject> subjects
        , String cellphoneNumber
        , LocalDate dateBirth
        , String resumeTeacher
        , String yearsExperience
        , String academicFormation
        , String hourlyRate
        , String profileImageContentType
        , byte[] profileImage
) implements Serializable {
}