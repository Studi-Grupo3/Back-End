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

    @AfterMapping
    default void validateAndUpdateFields(@MappingTarget Teacher teacher, @Valid TeacherRequestUpdateDTO dto) {
        if (dto.name() != null && teacher.getName() == null) {
            teacher.setName(dto.name());
        }
        if (dto.email() != null && teacher.getEmail() == null) {
            teacher.setEmail(dto.email());
        }
        if (dto.cpf() != null && teacher.getCpf() == null) {
            teacher.setCpf(dto.cpf());
        }
        if (dto.password() != null && teacher.getPassword() == null) {
            teacher.setPassword(dto.password());
        }
        if (dto.subject() != null && teacher.getSubject() == null) {
            teacher.setSubject(dto.subject());
        }
        if (dto.cellphoneNumber() != null && teacher.getCellphoneNumber() == null) {
            teacher.setCellphoneNumber(dto.cellphoneNumber());
        }
        if (dto.dateBirth() != null && teacher.getDateBirth() == null) {
            teacher.setDateBirth(dto.dateBirth());
        }
    }
}
