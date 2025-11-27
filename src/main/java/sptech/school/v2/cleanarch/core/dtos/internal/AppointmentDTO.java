package sptech.school.v2.cleanarch.core.dtos.internal;

import java.time.LocalDateTime;

public record AppointmentDTO(
        Integer idStudent,
        Integer idTeacher,
        LocalDateTime dateTime,
        Double lessonDuration,
        String location,
        Double totalValue,
        String status,
        String paymentStatus,
        String subject
) {}
