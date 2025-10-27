package sptech.school.v2.cleanarch.core.application.mappers;

import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import sptech.school.v2.cleanarch.core.dtos.out.ContentResponseDTO;
import sptech.school.v2.cleanarch.domain.entities.Content;

@Mapper(componentModel = "spring")
public interface ContentMapper {

    @Mapping(target = "formattedSize", source = "fileSize", qualifiedByName = "formatSize")
    @Mapping(target = "idStudent", source = "student.id")
    @Valid
    ContentResponseDTO toResponse(Content content);

    @Named("formatSize")
    static String formatSize(Long size) {
        if (size == null) return "0 B";
        String[] units = {"B", "KB", "MB", "GB"};
        int unitIndex = 0;
        double sizeDouble = size;

        while (sizeDouble >= 1024 && unitIndex < units.length - 1) {
            sizeDouble /= 1024;
            unitIndex++;
        }

        return String.format("%.1f %s", sizeDouble, units[unitIndex]);
    }
}
