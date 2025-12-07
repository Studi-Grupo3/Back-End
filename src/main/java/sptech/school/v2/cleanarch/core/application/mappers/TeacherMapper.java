package sptech.school.v2.cleanarch.core.application.mappers;

import jakarta.validation.Valid;
import org.mapstruct.*;
import sptech.school.v2.cleanarch.core.dtos.in.TeacherRequestDTO;
import sptech.school.v2.cleanarch.core.application.utils.StringMapperUtil;
import sptech.school.v2.cleanarch.core.dtos.in.teacher.TeacherRegisterDTO;
import sptech.school.v2.cleanarch.core.dtos.in.teacher.TeacherUpdateDTO;
import sptech.school.v2.cleanarch.core.dtos.out.teacher.TeacherResponseDTO;
import sptech.school.v2.cleanarch.domain.entities.ResourceFile;
import sptech.school.v2.cleanarch.domain.entities.Teacher;
import sptech.school.v2.cleanarch.domain.enumerated.Subject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses =  StringMapperUtil.class)
public interface TeacherMapper {
    TeacherUpdateDTO toDto(Teacher teacher);
    Teacher dtoUpdateToEntity(TeacherUpdateDTO dto);

    @Mapping(target = "profileImageContentType", source = "profileImage", qualifiedByName = "mapProfileImageContentType")
    @Mapping(target = "profileImage", source = "profileImage", qualifiedByName = "mapProfileImageBytes")
    @Mapping(target = "status", expression = "java(teacher.isDeleted() ? \"Inativo\" : \"Ativo\")")
    TeacherResponseDTO toDtoResponse(Teacher teacher);
    Teacher dtoRequestToEntity(TeacherRequestDTO dto);
    Teacher toEntity(TeacherRegisterDTO dto);

    @AfterMapping
    default void mapSubjectsOnCreate(@MappingTarget Teacher teacher, TeacherRegisterDTO dto) {
        List<Subject> list = new ArrayList<>();
        if (dto.subjects() != null && !dto.subjects().isEmpty()) {
            list.addAll(dto.subjects());
        }
        if (dto.subject() != null && !dto.subject().isBlank()) {
            try {
                list.add(Subject.valueOf(dto.subject()));
            } catch (IllegalArgumentException ignored) {
            }
        }
        if (!list.isEmpty()) {
            teacher.setSubjects(list);
        }
    }

    @Named("mapProfileImageUrl")
    default String mapProfileImageUrl(ResourceFile profileImage) {
        if (profileImage == null) {
            return null;
        }
        return profileImage.getFileLocation();
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTeacherFromDto(@Valid TeacherUpdateDTO dto, @MappingTarget Teacher teacherSaved);

    @AfterMapping
    default void validateAndUpdateFields(@MappingTarget Teacher teacherSaved, @Valid TeacherUpdateDTO teacherSended) {
        if (teacherSended.name() != null) {
            teacherSaved.setName(teacherSended.name());
        }
        if (teacherSended.email() != null) {
            teacherSaved.setEmail(teacherSended.email());
        }
        if (teacherSended.cpf() != null) {
            teacherSaved.setCpf(teacherSended.cpf());
        }
        if (teacherSended.cellphoneNumber() != null) {
            teacherSaved.setCellphoneNumber(teacherSended.cellphoneNumber());
        }
        if (teacherSended.dateBirth() != null) {
            teacherSaved.setDateBirth(teacherSended.dateBirth());
        }
        if (teacherSended.resumeTeacher() != null) {
            teacherSaved.setResumeTeacher(teacherSended.resumeTeacher());
        }
        if (teacherSended.yearsExperience() != null) {
            teacherSaved.setYearsExperience(teacherSended.yearsExperience());
        }
        if (teacherSended.academicFormation() != null) {
            teacherSaved.setAcademicFormation(teacherSended.academicFormation());
        }
        if (teacherSended.hourlyRate() != null) {
            teacherSaved.setHourlyRate(teacherSended.hourlyRate());
        }

        if (teacherSended.subjects() != null) {
            teacherSaved.setSubjects(teacherSended.subjects());
        } else if (teacherSended.subject() != null && !teacherSended.subject().isBlank()) {
            try {
                Subject s = Subject.valueOf(teacherSended.subject());
                List<Subject> only = new java.util.ArrayList<>();
                only.add(s);
                teacherSaved.setSubjects(only);
            } catch (IllegalArgumentException ignored) {
                // código inválido, ignora
            }
        }

        if (teacherSended.active() != null) {
            boolean active = teacherSended.active();
            teacherSaved.setDeleted(!active);
        }
    }

    @Mapping(target = "profileImageContentType", source = "profileImage", qualifiedByName = "mapProfileImageContentType")
    @Mapping(target = "profileImage", source = "profileImage", qualifiedByName = "mapProfileImageBytes")
    @Mapping(target = "status", expression = "java(teacherSaved.isDeleted() ? \"Inativo\" : \"Ativo\")")
    TeacherResponseDTO toResponseDTO(Teacher teacherSaved);

    @Named("mapProfileImageContentType")
    default String mapProfileImageContentType(ResourceFile profileImage) {
        if (profileImage == null) {
            return null;
        }
        return profileImage.getFileType();
    }

    @Named("mapProfileImageBytes")
    default byte[] mapProfileImageBytes(ResourceFile profileImage) {
        if (profileImage == null) {
            return null;
        }

        InputStream inputStream = profileImage.getInputStream();
        if (inputStream == null) {
            return null;
        }

        try {
            byte[] data = inputStream.readAllBytes();
            if (inputStream instanceof ByteArrayInputStream byteArrayInputStream) {
                byteArrayInputStream.reset();
            }
            return data;
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to read profile image data", exception);
        }
    }
}
