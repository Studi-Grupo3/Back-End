package sptech.school.v2.cleanarch.core.application.mappers;

import jakarta.validation.Valid;
import org.mapstruct.*;
import sptech.school.domain.dto.request.TeacherRequestDTO;
import sptech.school.v2.cleanarch.core.application.utils.StringMapperUtil;
import sptech.school.v2.cleanarch.core.dtos.in.teacher.TeacherUpdateDTO;
import sptech.school.v2.cleanarch.core.dtos.in.teacher.TeacherRegisterDTO;
import sptech.school.v2.cleanarch.core.dtos.out.teacher.TeacherResponseDTO;
import sptech.school.domain.entity.Teacher;

@Mapper(componentModel = "spring", uses =  StringMapperUtil.class)
public interface TeacherMapper {
    TeacherUpdateDTO toDto(Teacher teacher);
    Teacher dtoUpdateToEntity(TeacherUpdateDTO dto);
    TeacherResponseDTO toDtoResponse(Teacher teacher);
    Teacher dtoRequestToEntity(TeacherRequestDTO dto);
    Teacher toEntity(TeacherRegisterDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTeacherFromDto(@Valid TeacherUpdateDTO dto, @MappingTarget Teacher teacherSaved);

    @AfterMapping
    default void validateAndUpdateFields(@MappingTarget Teacher teacherSaved, @Valid TeacherUpdateDTO teacherSended) {
        System.out.println("Entrou no método");
        if (teacherSended.name() != null && teacherSaved.getName() == null) {
            System.out.println("Atualizou o Name");
            teacherSaved.setName(teacherSended.name());
        }
        if (teacherSended.email() != null && teacherSaved.getEmail() == null) {
            System.out.println("Atualizou o Email");
            teacherSaved.setEmail(teacherSended.email());
        }
        if (teacherSended.cpf() != null && teacherSaved.getCpf() == null) {
            System.out.println("Atualizou o Cpf");
            teacherSaved.setCpf(teacherSended.cpf());
        }
        if (teacherSended.subjects() != null && teacherSaved.getSubjects() == null) {
            System.out.println("Atualizou o Subject");
            teacherSaved.setSubjects(teacherSended.subjects());
        }
        if (teacherSended.cellphoneNumber() != null && teacherSaved.getCellphoneNumber() == null) {
            System.out.println("Atualizou o CellphoneNumber");
            teacherSaved.setCellphoneNumber(teacherSended.cellphoneNumber());
        }
        if (teacherSended.dateBirth() != null && teacherSaved.getDateBirth() == null) {
            System.out.println("Atualizou o DateBirth");
            teacherSaved.setDateBirth(teacherSended.dateBirth());
        }

        if (teacherSended.resumeTeacher() != null && teacherSaved.getResumeTeacher() == null) {
            System.out.println("Atualizou o resume");
            teacherSaved.setResumeTeacher(teacherSended.resumeTeacher());
        }

        if (teacherSended.yearsExperience() != null && teacherSaved.getYearsExperience() == null) {
            System.out.println("Atualizou o yearsExperience");
            teacherSaved.setYearsExperience(teacherSended.yearsExperience());
        }

        if (teacherSended.academicFormation() != null && teacherSaved.getAcademicFormation() == null) {
            System.out.println("Atualizou o academicFormation");
            teacherSaved.setAcademicFormation(teacherSended.academicFormation());
        }

        if (teacherSended.subjects() != null) {
            System.out.println("Atualizou o subjects");
            teacherSaved.setSubjects(teacherSended.subjects());
        }

        if (teacherSended.hourlyRate() != null && teacherSaved.getHourlyRate() == null) {
            System.out.println("Atualizou o hourlyRate");
            teacherSaved.setHourlyRate(teacherSended.hourlyRate());
        }
        System.out.println("Teacher: " + teacherSaved);
        System.out.println("Dto: " + teacherSended);
    }

    TeacherResponseDTO toResponseDTO(Teacher teacherSaved);
}