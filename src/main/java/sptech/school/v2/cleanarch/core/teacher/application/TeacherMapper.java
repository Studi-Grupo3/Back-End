package sptech.school.v2.cleanarch.core.teacher.application;

import jakarta.validation.Valid;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import sptech.school.application.mappers.utils.StringMapperUtil;
import sptech.school.domain.dto.request.TeacherRequestDTO;
import sptech.school.domain.dto.request.TeacherRequestUpdateDTO;
import sptech.school.v2.cleanarch.core.teacher.dtos.in.TeacherRegisterDTO;
import sptech.school.v2.cleanarch.core.teacher.dtos.out.TeacherResponseDTO;
import sptech.school.domain.entity.Teacher;

//@Mapper(componentModel = "spring", uses = StringMapperUtil.class)
@Mapper(componentModel = "spring")
public interface TeacherMapper {
    static TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);
    TeacherRequestUpdateDTO toDto(Teacher teacher);
    Teacher dtoUpdateToEntity(TeacherRequestUpdateDTO dto);
    TeacherResponseDTO toDtoResponse(Teacher teacher);
    Teacher dtoRequestToEntity(TeacherRequestDTO dto);
    Teacher toEntity(TeacherRegisterDTO dto);

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

        if (dto.resumeTeacher() != null && teacher.getResumeTeacher() == null) {
            System.out.println("Atualizou o resume");
            teacher.setResumeTeacher(dto.resumeTeacher());
        }

        if (dto.yearsExperience() != null && teacher.getYearsExperience() == null) {
            System.out.println("Atualizou o yearsExperience");
            teacher.setYearsExperience(dto.yearsExperience());
        }

        if (dto.academicFormation() != null && teacher.getAcademicFormation() == null) {
            System.out.println("Atualizou o academicFormation");
            teacher.setAcademicFormation(dto.academicFormation());
        }

        if (dto.subject() != null) {
            System.out.println("Atualizou o subject");
            teacher.setSubject(dto.subject());
        }

        if (dto.hourlyRate() != null && teacher.getHourlyRate() == null) {
            System.out.println("Atualizou o hourlyRate");
            teacher.setHourlyRate(dto.hourlyRate());
        }
        System.out.println("Teacher: " + teacher);
        System.out.println("Dto: " + dto);
    }

    TeacherResponseDTO toResponseDTO(Teacher teacherSaved);
}