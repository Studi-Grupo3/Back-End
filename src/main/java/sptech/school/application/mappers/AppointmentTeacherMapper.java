package sptech.school.application.mappers;

import org.mapstruct.Mapper;
import sptech.school.domain.dto.response.teacher.UpcomingLessonDTO;
import sptech.school.domain.dto.response.teacher.LessonHistoryDTO;
import sptech.school.domain.entity.Appointment;
import sptech.school.domain.enumerated.AppointmentStatus;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface AppointmentTeacherMapper {

    default UpcomingLessonDTO toUpcomingLessonDto(Appointment appointment) {
        if (appointment == null) {
            return null;
        }
        UpcomingLessonDTO dto = new UpcomingLessonDTO();
        dto.setId(appointment.getId());
        // subject via Teacher.subject
        if (appointment.getTeacher() != null && appointment.getTeacher().getSubjects() != null) {
            dto.setDisciplina(appointment.getTeacher().getSubjects().toString());
        }
        // aluno
        if (appointment.getStudent() != null) {
            dto.setStudentId(appointment.getStudent().getId());
            dto.setStudentName(appointment.getStudent().getName());
        }
        // date/time
        LocalDateTime dt = appointment.getDateTime();
        if (dt != null) {
            dto.setDate(dt.toLocalDate());
            dto.setTime(dt.toLocalTime());
        }
        dto.setLessonDuration(appointment.getLessonDuration());
        dto.setLocation(appointment.getLocation());
        dto.setLocation(appointment.getLocation());
        dto.setStatus(appointment.getStatus() != null ? appointment.getStatus().name() : null);
        return dto;
    }

    default LessonHistoryDTO toLessonHistoryDto(Appointment appointment) {
        if (appointment == null) {
            return null;
        }
        LessonHistoryDTO dto = new LessonHistoryDTO();
        dto.setId(appointment.getId());
        if (appointment.getTeacher() != null && appointment.getTeacher().getSubjects() != null) {
            dto.setSubject(appointment.getTeacher().getSubjects().toString());
        }
        if (appointment.getStudent() != null) {
            dto.setStudentId(appointment.getStudent().getId());
            dto.setStudentName(appointment.getStudent().getName());
        }
        LocalDateTime dt = appointment.getDateTime();
        if (dt != null) {
            dto.setDate(dt.toLocalDate());
            dto.setTime(dt.toLocalTime());
        }
        dto.setDuration(appointment.getLessonDuration());
        dto.setLocation(appointment.getLocation());
        dto.setOnline(appointment.getLocation() != null
                && "Online".equalsIgnoreCase(appointment.getLocation()));
        dto.setStatus(appointment.getStatus() != null ? appointment.getStatus().name() : null);
        dto.setTotalValue(appointment.getTotalValue());
        // motivoCancelamento / cancelDateTime: se você tiver campos em Appointment, preencha aqui.
        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            // Exemplo: se você tiver campos appointment.getCancelReason(), appointment.getCancelDateTime()
            // dto.setMotivoCancelamento(appointment.getCancelReason());
            // dto.setCancelDateTime(appointment.getCancelDateTime());
            dto.setMotivoCancelamento(null);
            dto.setCancelDateTime(null);
        }
        return dto;
    }
}
