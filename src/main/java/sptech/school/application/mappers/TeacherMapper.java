package sptech.school.application.mappers;

import jakarta.validation.Valid;
import org.mapstruct.*;
import sptech.school.application.mappers.utils.StringMapperUtil;
import sptech.school.domain.dto.request.TeacherRequestDTO;
import sptech.school.domain.dto.request.TeacherRequestUpdateDTO;
import sptech.school.domain.dto.response.TeacherResponseDTO;
import sptech.school.domain.entity.Teacher;

@Mapper(componentModel = "spring", uses = StringMapperUtil.class)
public interface TeacherMapper {
    TeacherRequestUpdateDTO toDto(Teacher teacher);
    Teacher dtoUpdateToEntity(TeacherRequestUpdateDTO dto);
    TeacherResponseDTO toDtoResponse(Teacher teacher);
    Teacher dtoRequestToEntity(TeacherRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTeacherFromDto(@Valid TeacherRequestUpdateDTO dto, @MappingTarget Teacher teacher);
}