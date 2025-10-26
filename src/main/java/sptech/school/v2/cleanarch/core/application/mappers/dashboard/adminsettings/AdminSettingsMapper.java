package sptech.school.v2.cleanarch.core.application.mappers.dashboard.adminsettings;

import org.mapstruct.*;
import sptech.school.v2.cleanarch.domain.entities.Admin;
import org.mapstruct.NullValuePropertyMappingStrategy;
import sptech.school.v2.cleanarch.core.dtos.in.dashboard.adminsettings.AdminSettingsRequestDTO;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.adminsettings.AdminSettingsResponseDTO;

@Mapper(componentModel = "spring")
public interface AdminSettingsMapper {

    AdminSettingsResponseDTO toResponse(Admin admin);

    @Mapping(target = "password", ignore = true)
    void overwriteFromDto(AdminSettingsRequestDTO dto, @MappingTarget Admin admin);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "password", ignore = true)
    void updateFromDtoIgnoreNull(AdminSettingsRequestDTO dto, @MappingTarget Admin admin);
}
