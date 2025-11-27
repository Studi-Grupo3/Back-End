package sptech.school.v2.cleanarch.core.application.mappers.appointment;

import jakarta.validation.Valid;
import org.mapstruct.*;
import sptech.school.v2.cleanarch.core.dtos.internal.AppointmentDTO;
import sptech.school.v2.cleanarch.core.dtos.out.AppointmentResponseDTO;
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
    @Mapping(target = "professorTitle", expression = "java(\"Professor(a) de \" + (appointment.getTeacher() != null ? appointment.getTeacher().getSubjects() : \"\"))")
    @Mapping(target = "professorImageUrl", constant = "/lovable-uploads/09a24ead-9c40-487a-a233-8c1f43dcc6df.png")
    @Mapping(target = "dateTime", source = "dateTime")
    @Mapping(target = "duration", source = "lessonDuration")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "totalValue", source = "totalValue")
    @Mapping(target = "location", source = "location")
    @Mapping(target = "subject", source = "subject")
    @Mapping(target = "online", expression = "java(appointment.getLocation() != null && appointment.getLocation().equalsIgnoreCase(\"Online\"))")
    AppointmentResponseDTO toResponseDto(Appointment appointment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    void updateFromDto(@Valid AppointmentDTO dto, @MappingTarget Appointment appointment);
}
