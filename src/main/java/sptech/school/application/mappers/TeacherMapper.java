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
        System.out.println("Entrou no método");
        if (dto.name() != null && teacher.getName() == null) {
            System.out.println("Atualizou o Name");
            teacher.setName(dto.name());
        }
        if (dto.email() != null && teacher.getEmail() == null) {
            System.out.println("Atualizou o Email");
            teacher.setEmail(dto.email());
        }
        if (dto.cpf() != null && teacher.getCpf() == null) {
            System.out.println("Atualizou o Cpf");
            teacher.setCpf(dto.cpf());
        }
        if (dto.subject() != null && teacher.getSubject() == null) {
            System.out.println("Atualizou o Subject");
            teacher.setSubject(dto.subject());
        }
        if (dto.cellphoneNumber() != null && teacher.getCellphoneNumber() == null) {
            System.out.println("Atualizou o CellphoneNumber");
            teacher.setCellphoneNumber(dto.cellphoneNumber());
        }
        if (dto.dateBirth() != null && teacher.getDateBirth() == null) {
            System.out.println("Atualizou o DateBirth");
            teacher.setDateBirth(dto.dateBirth());
        }

        if (dto.resume() != null && teacher.getResumeTeacher() == null) {
            System.out.println("Atualizou o resume");
            teacher.setResumeTeacher(dto.resume());
        }
        System.out.println("Teacher: " + teacher);
        System.out.println("Dto: " + dto);
    }
}
