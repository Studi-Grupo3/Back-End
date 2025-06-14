package sptech.school.application.mappers;

import jakarta.validation.Valid;
import org.mapstruct.*;
import sptech.school.application.mappers.utils.StringMapperUtil;
import sptech.school.domain.dto.request.StudentRequestDTO;
import sptech.school.domain.dto.request.StudentRequestUpdateDTO;
import sptech.school.domain.dto.response.StudentResponseDTO;
import sptech.school.domain.entity.Responsible;
import sptech.school.domain.entity.Student;

@Mapper(componentModel = "spring", uses = StringMapperUtil.class)
public interface StudentMapper {
    StudentRequestUpdateDTO toDto(Student student);
    Student toEntity(StudentRequestUpdateDTO dto);
    StudentResponseDTO toDtoResponse(Student student);
    Student dtoRequestToEntity(StudentRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateStudentFromDto(@Valid StudentRequestUpdateDTO studentRequestUpdateDTO, @MappingTarget Student student);

    @AfterMapping
    default void afterMappingUpdateStudentFromDto(StudentRequestUpdateDTO dto, @MappingTarget Student student) {
        if (dto.cpf() != null && student.getCpf() == null) {
            student.setCpf(dto.cpf());
        }
        if (dto.cellphoneNumber() != null && student.getCellphoneNumber() == null) {
            student.setCellphoneNumber(dto.cellphoneNumber());
        }
        if (dto.dateBirth() != null && student.getDateBirth() == null) {
            student.setDateBirth(dto.dateBirth());
        }
        if (dto.schoolGrade() != null && student.getSchoolGrade() == null) {
            student.setSchoolGrade(dto.schoolGrade());
        }
        if (dto.schoolName() != null && student.getSchoolName() == null) {
            student.setSchoolName(dto.schoolName());
        }
        if (dto.responsible() != null) {
            if (student.getResponsible() == null) {
                student.setResponsible(dto.responsible());
            } else {
                Responsible responsible = student.getResponsible();
                if (dto.responsible().getResponsibleName() != null && responsible.getResponsibleName() == null) {
                    responsible.setResponsibleName(dto.responsible().getResponsibleName());
                }
                if (dto.responsible().getResponsibleCellphoneNumber() != null && responsible.getResponsibleCellphoneNumber() == null) {
                    responsible.setResponsibleCellphoneNumber(dto.responsible().getResponsibleCellphoneNumber());
                }
                if (dto.responsible().getKinship() != null && responsible.getKinship() == null) {
                    responsible.setKinship(dto.responsible().getKinship());
                }
                if (dto.responsible().getResponsibleCpf() != null && responsible.getResponsibleCpf() == null) {
                    responsible.setResponsibleCpf(dto.responsible().getResponsibleCpf());
                }
                if (dto.responsible().getResponsibleEmail() != null && responsible.getResponsibleEmail() == null) {
                    responsible.setResponsibleEmail(dto.responsible().getResponsibleEmail());
                }

            }
        }
    }
}
