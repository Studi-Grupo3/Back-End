package sptech.school.v2.cleanarch.core.dtos.out;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ContentResponseDTO(
        @NotBlank String fileName,
        @NotBlank String fileType,
        @NotBlank Long fileSize,
        @NotBlank String formattedSize,
        @NotNull Integer idStudent
) {}
