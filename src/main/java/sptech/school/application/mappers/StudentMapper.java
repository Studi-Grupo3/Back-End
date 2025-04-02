package sptech.school.application.mappers;

import jakarta.validation.Valid;
import org.mapstruct.*;
import sptech.school.application.mappers.utils.StringMapperUtil;
import sptech.school.domain.dto.request.StudentRequestDTO;
import sptech.school.domain.dto.request.StudentRequestUpdateDTO;
import sptech.school.domain.dto.response.StudentResponseDTO;
import sptech.school.domain.entity.Student;

@Mapper(componentModel = "spring", uses = StringMapperUtil.class)
public interface StudentMapper {
    StudentRequestUpdateDTO toDto(Student student);
    Student toEntity(StudentRequestUpdateDTO dto);
    StudentResponseDTO toDtoResponse(Student student);
    Student dtoRequestToEntity(StudentRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateStudentFromDto(@Valid StudentRequestUpdateDTO studentRequestUpdateDTO, @MappingTarget Student student);
}