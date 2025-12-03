package sptech.school.v2.cleanarch.core.application.mappers.appointment;

import jakarta.validation.Valid;
import org.mapstruct.*;
import sptech.school.v2.cleanarch.core.dtos.internal.AppointmentDTO;
import sptech.school.v2.cleanarch.core.dtos.out.appointment.AppointmentResponseDTO;
import sptech.school.v2.cleanarch.domain.entities.Appointment;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(source = "student.id", target = "idStudent")
    @Mapping(source = "teacher.id", target = "idTeacher")
    AppointmentDTO toDto(Appointment appointment);

    @Mapping(target = "student", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    Appointment toEntity(@Valid AppointmentDTO dto);

    @Mapping(target = "professorName", source = "teacher.name")
    @Mapping(target = "professorTitle", expression = "java(\"Professor(a) de \" + appointment.getSubject())")
    @Mapping(target = "professorImageUrl", expression = "java(mapProfessorImageUrl(appointment))")
    @Mapping(target = "professorPhone", source = "teacher.cellphoneNumber")
    @Mapping(target = "dateTime", source = "dateTime")
    @Mapping(target = "duration", source = "lessonDuration")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "totalValue", source = "totalValue")
    @Mapping(target = "location", source = "location")
    @Mapping(target = "subject", source = "subject")
    @Mapping(target = "online", expression = "java(appointment.getLocation() != null && appointment.getLocation().equalsIgnoreCase(\"Online\"))")
    AppointmentResponseDTO toResponseDto(Appointment appointment);

    default String mapProfessorImageUrl(Appointment a) {
        String name = a.getTeacher() != null ? a.getTeacher().getName() : null;
        if (name == null) return "/images/professors/default.png";
        String key = name.toLowerCase();
        if (key.contains("carlos lima")) return "/images/professors/carlos-lima.png";
        if (key.contains("rodrigo santos")) return "/images/professors/rodrigo-santos.png";
        if (key.contains("gustavo pereira")) return "/images/professors/gustavo-pereira.png";
        if (key.contains("joão neto") || key.contains("joao neto")) return "/images/professors/joao-neto.png";
        if (key.contains("marcos vinicius")) return "/images/professors/marcos-vinicius.png";
        if (key.contains("beatriz costa")) return "/images/professors/beatriz-costa.png";
        if (key.contains("fernanda alvez")) return "/images/professors/fernanda-alvez.png";
        if (key.contains("marina oliveira")) return "/images/professors/marina-oliveira.png";
        if (key.contains("helena moura")) return "/images/professors/helena-moura.png";
        if (key.contains("carla mendes")) return "/images/professors/carla-mendes.png";
        return "/images/professors/default.png";
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    void updateFromDto(@Valid AppointmentDTO dto, @MappingTarget Appointment appointment);
}
