package sptech.school.application.mappers;

import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sptech.school.domain.dto.AppointmentDTO;
import sptech.school.domain.dto.response.AppointmentResponseDTO;
import sptech.school.domain.entity.Appointment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(source = "student.id", target = "idStudent")
    @Mapping(source = "teacher.id", target = "idTeacher")
    AppointmentDTO toDto(Appointment appointment);

    Appointment toEntity(@Valid AppointmentDTO dto);

    @Mapping(target = "subjects", source = "teacher.subjects")
    @Mapping(target = "professorName", source = "teacher.name")
    @Mapping(target = "professorTitle", expression = "java(\"Professor(a) de \" + appointment.getTeacher().getSubjects())")
    @Mapping(target = "professorImageUrl", constant = "/lovable-uploads/09a24ead-9c40-487a-a233-8c1f43dcc6df.png")
    @Mapping(target = "dateTime", source = "dateTime")
    @Mapping(target = "duration", source = "lessonDuration")  // Diretamente mapeando o campo lessonDuration
    @Mapping(target = "status", source = "status")
    @Mapping(target = "totalValue", source = "totalValue")
    @Mapping(target = "location", source = "location")
    @Mapping(target = "online", expression = "java(appointment.getLocation().equalsIgnoreCase(\"Online\"))")
    AppointmentResponseDTO toResponseDto(Appointment appointment);
}

