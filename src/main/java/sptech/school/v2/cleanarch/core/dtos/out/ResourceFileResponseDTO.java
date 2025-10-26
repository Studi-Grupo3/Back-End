package sptech.school.v2.cleanarch.core.dtos.out;

import jakarta.validation.constraints.NotBlank;

public record ResourceFileResponseDTO(
        @NotBlank String fileName
        , @NotBlank String fileType
        , @NotBlank Long fileSize
        , @NotBlank String formattedSize
) { }
