package sptech.school.domain.dto;

import java.time.LocalDateTime;

public record AppointmentDTO(
        Integer idStudent,
        Integer idTeacher,
        LocalDateTime dateTime,
        Double lessonDuration,
        String location,
        Double totalValue,
        String status
) {}
