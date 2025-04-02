package sptech.school.domain.dto.response;

import jakarta.validation.constraints.NotBlank;

public record ResourceFileResponseDTO(
        @NotBlank String fileName
        , @NotBlank String fileType
        , @NotBlank Long fileSize
        , @NotBlank String formattedSize
) { }
